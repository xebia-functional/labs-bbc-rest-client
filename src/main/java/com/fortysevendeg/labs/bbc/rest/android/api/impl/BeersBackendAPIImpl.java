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

package com.fortysevendeg.labs.bbc.rest.android.api.impl;

import android.content.Context;
import com.fortysevendeg.labs.bbc.rest.android.R;
import com.fortysevendeg.labs.bbc.rest.android.api.BeersBackendAPI;
import com.fortysevendeg.labs.bbc.rest.android.api.request.BeerRequest;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerListResponse;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerResponse;
import it.restrung.rest.client.APICredentialsDelegate;
import it.restrung.rest.client.APIDelegate;
import it.restrung.rest.client.RestClient;
import it.restrung.rest.client.RestClientFactory;

public class BeersBackendAPIImpl implements BeersBackendAPI {

    private RestClient client = RestClientFactory.getClient();

    private String endpoint;

    private APICredentialsDelegate apiCredentialsDelegate;

    @Override
    public synchronized void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private String getEndpoint(String path) {
        return endpoint + path;
    }

    @Override
    public APICredentialsDelegate getApiCredentialsDelegate() {
        return apiCredentialsDelegate;
    }

    @Override
    public synchronized void setApiCredentialsDelegate(APICredentialsDelegate apiCredentialsDelegate) {
        this.apiCredentialsDelegate = apiCredentialsDelegate;
    }

    @Override
    public void init(Context context, APICredentialsDelegate apiCredentialsDelegate) {
        setEndpoint(context.getString(R.string.end_point));
        setApiCredentialsDelegate(apiCredentialsDelegate);
    }

    @Override
    public void getBeers(APIDelegate<BeerListResponse> delegate) {
        client.getAsync(delegate, getEndpoint("/beers"));
    }

    @Override
    public void getBeer(String beerId, APIDelegate<BeerResponse> delegate) {
        client.getAsync(delegate, getEndpoint("/beers/%s"), beerId);
    }

    @Override
    public void addBeer(BeerRequest beerRequest, APIDelegate<BeerResponse> delegate) {
        client.putAsync(delegate, getEndpoint("/beers"), beerRequest);
    }

    @Override
    public void deleteBeer(String beerId, APIDelegate<BeerResponse> delegate) {
        client.deleteAsync(delegate, getEndpoint("/beers/%s"), null, beerId);
    }

    @Override
    public void updateBeer(String beerId, BeerRequest beerRequest, APIDelegate<BeerResponse> delegate) {
        client.postAsync(delegate, getEndpoint("/beers/%s"), beerRequest, beerId);
    }
}
