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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.fortysevendeg.labs.bbc.rest.android.R;
import com.fortysevendeg.labs.bbc.rest.android.api.APIService;
import com.fortysevendeg.labs.bbc.rest.android.api.request.BeerRequest;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerResponse;
import it.restrung.rest.client.ContextAwareAPIDelegate;

public class BeerActivity extends Activity {

    public static String KEY_BEER_EDIT = "KEY_BEER_EDIT";

    private BeerResponse beerResponseEdit;

    private EditText etName;
    private EditText etAlcohol;
    private EditText etDescription;

    /**
     * @see Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(KEY_BEER_EDIT)) {
            beerResponseEdit = (BeerResponse) getIntent().getExtras().getSerializable(KEY_BEER_EDIT);
        }

        setContentView(R.layout.beer_activity);

        etName = (EditText) findViewById(R.id.beer_name);
        etAlcohol = (EditText) findViewById(R.id.beer_alcohol);
        etDescription = (EditText) findViewById(R.id.beer_description);

        if (beerResponseEdit != null) {
            getActionBar().setTitle(beerResponseEdit.getName());
            etName.setText(beerResponseEdit.getName());
            etAlcohol.setText(String.valueOf(beerResponseEdit.getAvb()));
            etDescription.setText(beerResponseEdit.getDescription());
        } else {
            getActionBar().setTitle(R.string.newBeer);
        }

    }

    /**
     * @see android.support.v4.app.FragmentActivity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (beerResponseEdit!=null) {
            inflater.inflate(R.menu.beer_edit_menu, menu);
        } else {
            inflater.inflate(R.menu.beer_add_menu, menu);
        }
        return true;
    }

    /**
     * @see android.support.v4.app.FragmentActivity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            addBeer();
        }
        if (item.getItemId() == R.id.menu_delete) {
            deleteBeer();
        }
        if (item.getItemId() == R.id.menu_update) {
            updateBeer();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update beer on server
     */
    private void updateBeer() {
        if (TextUtils.isEmpty(etAlcohol.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())) {
            Toast.makeText(BeerActivity.this,R.string.fieldsEmpty,Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(getString(R.string.connecting));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        BeerRequest beerRequest = new BeerRequest();
        beerRequest.setAvb(Double.parseDouble(etAlcohol.getText().toString()));
        beerRequest.setName(etName.getText().toString());
        beerRequest.setDescription(etDescription.getText().toString());
        APIService.get().updateBeer(beerResponseEdit.getId(), beerRequest, new ContextAwareAPIDelegate<BeerResponse>(this, BeerResponse.class) {
            @Override
            public void onResults(BeerResponse beerResponse) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this, R.string.beerSaved, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Delete beer on server
     */
    private void deleteBeer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(getString(R.string.connecting));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        APIService.get().deleteBeer(beerResponseEdit.getId(), new ContextAwareAPIDelegate<BeerResponse>(this, BeerResponse.class) {
            @Override
            public void onResults(BeerResponse beerResponse) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this, R.string.beerDeleted, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Add beer on server
     */
    private void addBeer() {
        if (TextUtils.isEmpty(etAlcohol.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())) {
            Toast.makeText(BeerActivity.this,R.string.fieldsEmpty,Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(getString(R.string.connecting));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        BeerRequest beerRequest = new BeerRequest();
        beerRequest.setAvb(Double.parseDouble(etAlcohol.getText().toString()));
        beerRequest.setName(etName.getText().toString());
        beerRequest.setDescription(etDescription.getText().toString());
        APIService.get().addBeer(beerRequest, new ContextAwareAPIDelegate<BeerResponse>(this, BeerResponse.class) {
            @Override
            public void onResults(BeerResponse beerResponse) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this,R.string.beerSaved,Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(BeerActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
            }
        });
    }

}