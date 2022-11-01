package com.network.watchbywebview.UI.Activities




import android.annotation.SuppressLint
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.R
import com.network.watchbywebview.UI.Adapters.FavoriteAdapter
import com.network.watchbywebview.UI.Fragments.DisplaySearchResultFragment
import com.network.watchbywebview.UI.Fragments.FavoriteFragment
import com.network.watchbywebview.UI.Fragments.MainFragment
import com.network.watchbywebview.ViewModel.FavoriteViewModel
import com.network.watchbywebview.ViewModel.SearchResultVM
import com.network.watchbywebview.ViewModel.WDSourceVM


class MainActivity : AppCompatActivity() {

    private lateinit var adservers: StringBuilder
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var searchResultContainer: FrameLayout
    lateinit var border: View
    private var CURRENT_FRAGMENT = 0
    private val MAIN_FRAGMENT = 0
    private val FAVORITE_FRAGMENT = 1
    private val WEBVIEW_FRAGMENT = 2
    private val NIGHTE_MODE = true
    private val DAY_MODE = false
    private val TWO_MILLI_SECONDS = 2000
    private var CURRENT_TIME_MILLI_SECONDS:Long = 0

    //lateinit var mainAdapter: MainAdapter
    private val mainResourceList = ArrayList<WDSource>()
    private val favoriteResourceList = ArrayList<WDSource>()
    private val searchResultVM: SearchResultVM by viewModels()
    private val wdSourceVM: WDSourceVM by viewModels()
    val favoriteVM: FavoriteViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        //supportActionBar?.hide()
        //setTheme(android.R.style.ThemeOverlay_Material_Dark)
        installSplashScreen()
        themeChanger(getModeValue())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchResultContainer = findViewById<FrameLayout>(R.id.searchResultContainer)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        wdSourceVM.getAll.observe(this, Observer { list ->
            if (mainResourceList.isNotEmpty()) {
                mainResourceList.clear()
            }
            mainResourceList.addAll(list)
        })

        favoriteVM.getAll.observe(this, Observer { list ->
            if (favoriteResourceList.isNotEmpty()) {
                favoriteResourceList.clear()
            }
            favoriteResourceList.addAll(list)
        })



        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fav -> {
                    fragmentSwitcher(FavoriteFragment(), FAVORITE_FRAGMENT)
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.home -> {
                    fragmentSwitcher(MainFragment(), MAIN_FRAGMENT)
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.shareApp -> {
                    share()
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.otherApps -> {
                    otherApps()
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.contactUs -> {
                    contactUs()
                    drawerLayout.closeDrawer(navigationView)
                }
            }
            true
        }
        switchModeListener(navigationView.menu)




        fragmentSwitcher(MainFragment(), MAIN_FRAGMENT)

        displaySearchResultFragment(DisplaySearchResultFragment())

        search(searchView)


    }

    private fun switchModeListener(menu: Menu) {
        val item = menu.findItem(R.id.switchMode)
        item.setActionView(R.layout.switch_layout)
        val switcher = item.actionView.findViewById<SwitchCompat>(R.id.modeSwitcher)

        switcher.isChecked = getModeValue()

        switcher.setOnCheckedChangeListener { _, state ->
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.modeValue), state)
                apply()
            }

            TaskStackBuilder.create(this)
                .addNextIntent(Intent(this, MainActivity::class.java))
                .addNextIntent(this.intent)
                .startActivities()
        }

    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                println("Search Keyword: $keyword")
                println("List: $favoriteResourceList")
                if (keyword != null && keyword.isEmpty()) {
                    searchResultContainer.visibility = View.GONE
                } else if (keyword != null && keyword.length > 2) {
                    when (CURRENT_FRAGMENT) {
                        MAIN_FRAGMENT -> filterSource(keyword, mainResourceList)
                        FAVORITE_FRAGMENT -> filterSource(keyword, favoriteResourceList)
                    }
                }

                return true
            }
        })
    }


    fun fragmentSwitcher(fragment: Fragment, id: Int) {
        CURRENT_FRAGMENT = id
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
        searchResultContainer.visibility = View.GONE
    }

    private fun displaySearchResultFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.searchResultContainer, fragment)
            .commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun themeChanger(state: Boolean) {

        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

    }

    private fun share() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            val shareMessage =
                "Hi There Try Our Great App, Get It Now on: https://example.com/some-random-text"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }

    private fun otherApps() {
        val uri: Uri = Uri.parse("https://play.google.com/store/apps/dev?id=6605125519975771237")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun contactUs() {
        val uri: Uri = Uri.parse("mailto:contact@me.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun filterSource(keyword: String?, list: ArrayList<WDSource>) {
        val searchList = ArrayList<WDSource>()
        if (keyword != null && keyword.length > 3) {
            for (index in 0 until list.size) {
                if (list[index].sourceName.lowercase().startsWith(keyword.lowercase())) {
                    val result = list[index]
                    searchList.add(result)
                }
            }

            println("setResult : ${searchList.size}")
            searchResultVM.setResult(searchList)
            searchResultContainer.visibility = View.VISIBLE
        }
    }

    fun getModeValue(): Boolean {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(getString(R.string.modeValue), false)
    }

    override fun onBackPressed() {
        if(CURRENT_FRAGMENT != MAIN_FRAGMENT){
            fragmentSwitcher(MainFragment(), MAIN_FRAGMENT)
        }else if(
            CURRENT_TIME_MILLI_SECONDS + TWO_MILLI_SECONDS > System.currentTimeMillis()
        ){
            super.onBackPressed()
        }else{
            Toast.makeText(this,R.string.pressTwiceToExit,Toast.LENGTH_SHORT).show()
            CURRENT_TIME_MILLI_SECONDS = System.currentTimeMillis()
        }
    }
}