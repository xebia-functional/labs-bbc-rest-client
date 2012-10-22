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

package com.fortysevendeg.labs.bbc.rest.android.api;

import android.content.Context;
import com.fortysevendeg.labs.bbc.rest.android.api.request.BeerRequest;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerListResponse;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerResponse;
import it.restrung.rest.client.APICredentialsDelegate;
import it.restrung.rest.client.APIDelegate;


public interface BeersBackendAPI {

    /**
     * Sets the API credentials delegate that will get notified of API changes
     *
     * @param apiCredentialsDelegate implementer that will receive API authentication notifications
     */
    void setApiCredentialsDelegate(APICredentialsDelegate apiCredentialsDelegate);

    /**
     * @return the api credentials delegate
     */
    APICredentialsDelegate getApiCredentialsDelegate();

    /**
     * Initializes this service, required before the api is used
     *
     * @param context
     */
    void init(Context context, APICredentialsDelegate apiCredentialsDelegate);

    /**
     * Sets the API service endpoint
     *
     * @param endpoint ex. https://api.ride.com/api/v1/ride
     */
    void setEndpoint(String endpoint);


    /**
     * Fetch the list of beers
     *
     * @param delegate Listener
     */
    void getBeers(APIDelegate<BeerListResponse> delegate);

    /**
     * Beer information
     *
     * @param beerId   Beer id
     * @param delegate Listener
     */
    void getBeer(String beerId, APIDelegate<BeerResponse> delegate);

    /**
     * Add beer to server
     *
     * @param beerRequest Products
     * @param delegate    listener
     */
    void addBeer(BeerRequest beerRequest, APIDelegate<BeerResponse> delegate);

    /**
     * Remove beer to server
     *
     * @param beerId   Beer id
     * @param delegate listener
     */
    void deleteBeer(String beerId, APIDelegate<BeerResponse> delegate);

    /**
     * Update beer to server
     *
     * @param beerId      Beer id
     * @param beerRequest Request
     * @param delegate    listener
     */
    public void updateBeer(String beerId, BeerRequest beerRequest, APIDelegate<BeerResponse> delegate);


}
