/*
 * Copyright (C) 2012 47 Degrees, LLC
 * http://47deg.com
 * hello@47deg.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortysevendeg.labs.bbc.rest.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.fortysevendeg.labs.bbc.rest.android.R;
import com.fortysevendeg.labs.bbc.rest.android.adapters.BeersAdapter;
import com.fortysevendeg.labs.bbc.rest.android.api.APIService;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerListResponse;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerResponse;
import it.restrung.rest.client.ContextAwareAPIDelegate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private static final int BEER_ACTIVITY = 0;

    private BeersAdapter beersAdapter;
    private List<BeerResponse> beers = new ArrayList<BeerResponse>();

    private ListView listView;

    /**
     * @see Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIService.get().init(this, null);

        setContentView(R.layout.main_activity);

        getActionBar().setTitle(R.string.titleMain);
        getActionBar().setSubtitle(R.string.subtitleMain);

        beersAdapter = new BeersAdapter(this, beers);

        listView = (ListView) findViewById(R.id.main_lv_list);
        listView.setEmptyView(findViewById(R.id.main_tv_empty));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BeerActivity.class);
                intent.putExtra(BeerActivity.KEY_BEER_EDIT, beersAdapter.getItem(position));
                startActivityForResult(intent, BEER_ACTIVITY);
            }
        });
        listView.setAdapter(beersAdapter);

        loadBeers();

    }

    /**
     * @see FragmentActivity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @see FragmentActivity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(this, BeerActivity.class);
            startActivityForResult(intent, BEER_ACTIVITY);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Load beers from server
     */
    private void loadBeers() {
        APIService.get().getBeers(new ContextAwareAPIDelegate<BeerListResponse>(this, BeerListResponse.class) {
            @Override
            public void onResults(BeerListResponse beerListResponse) {
                beers.clear();
                beers.addAll(beerListResponse.getBeers());
                beersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BEER_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    loadBeers();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

