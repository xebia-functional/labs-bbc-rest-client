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

package com.fortysevendeg.labs.bbc.rest.android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fortysevendeg.labs.bbc.rest.android.R;
import com.fortysevendeg.labs.bbc.rest.android.api.response.BeerResponse;

import java.util.List;

public class BeersAdapter extends BaseAdapter {

    public static class BeerViewHolder {
        public TextView tvName;
        public TextView tvAlcohol;
        public TextView tvDescription;
    }

    /**
     * Data for list adapter
     */
    private List<BeerResponse> data;

    /**
     * Context
     */
    private final Context context;


    /**
     * @see android.widget.BaseAdapter#BaseAdapter()
     */
    public BeersAdapter(Context context, List<BeerResponse> data) {
        this.context = context;
        this.data = data;
    }

    /**
     * @see android.widget.BaseAdapter#getCount()
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * @see android.widget.BaseAdapter#getItem(int)
     */
    @Override
    public BeerResponse getItem(int position) {
        return data.get(position);
    }

    /**
     * @see android.widget.BaseAdapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @see android.widget.BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BeerResponse item = data.get(position);
        BeerViewHolder beerViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.beer_row, null);
            beerViewHolder = new BeerViewHolder();
            beerViewHolder.tvName = (TextView) convertView.findViewById(R.id.beer_row_name);
            beerViewHolder.tvAlcohol = (TextView) convertView.findViewById(R.id.beer_row_alcohol);
            beerViewHolder.tvDescription = (TextView) convertView.findViewById(R.id.beer_row_description);
            convertView.setTag(beerViewHolder);
        } else {
            beerViewHolder = (BeerViewHolder) convertView.getTag();
        }

        beerViewHolder.tvName.setText(item.getName());
        beerViewHolder.tvAlcohol.setText(String.valueOf(item.getAvb()));
        beerViewHolder.tvDescription.setText(item.getDescription());

        return convertView;
    }

}