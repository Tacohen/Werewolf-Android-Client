package edu.wm.werewolf_client;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment; 
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainInterface extends Activity{
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_tabsholder);
 
	    Bundle b = this.getIntent().getExtras();
		final String username = b.getString("username");
		final String password = b.getString("password");
		
		UsernameAndPassword.setUsername(username);
		UsernameAndPassword.setPassword(password);
		
	    ActionBar bar = getActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    ActionBar.Tab userTab = bar.newTab().setText("Statistics");
	    ActionBar.Tab dayTab = bar.newTab().setText("Day Actions");
	    ActionBar.Tab nightTab = bar.newTab().setText("Night Actions");

	    Fragment userFragment = new UserTab();
	    Fragment dayFragment = new DayTab();
	    Fragment nightFragment = new NightTab();

	    userTab.setTabListener(new MyTabsListener(userFragment));
	    dayTab.setTabListener(new MyTabsListener(dayFragment));
	    nightTab.setTabListener(new MyTabsListener(nightFragment));

	    bar.addTab(userTab);
	    bar.addTab(dayTab);
	    bar.addTab(nightTab);

	}

	protected class MyTabsListener implements ActionBar.TabListener {

	    private Fragment fragment;

	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    	ft.remove(fragment);
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        ft.remove(fragment);
	    }

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction ft) {
			ft.add(R.id.fragment_container, fragment, null);
			
		}
	}
	


	

}
