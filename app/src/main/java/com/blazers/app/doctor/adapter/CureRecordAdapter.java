package com.blazers.app.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.blazers.app.doctor.R;
import it.gmariotti.cardslib.library.cards.topcolored.TopColoredCard;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by liang on 2015/5/25.
 */
public class CureRecordAdapter extends BaseAdapter {


    private final Context ctx;
    private final LayoutInflater inflater;

    public CureRecordAdapter(Context ctx) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cure_record_card, parent, false);
        }
        /* Init Card From Database */
        CardViewNative cardViewNative = (CardViewNative) convertView.findViewById(R.id.card);

        TopColoredCard card = TopColoredCard.with(ctx)
                .setColorResId(R.color.lightblue500)
                .setTitleOverColor("2015年5月25日 - 心脏搭桥手术")
                .setSubTitleOverColor("上海第八人民医院")
                .setupSubLayoutId(R.layout.card_top_colored_sub)
                .setupInnerElements(new TopColoredCard.OnSetupInnerElements() {
                    @Override
                    public void setupInnerViewElementsSecondHalf(View secondHalfView) {

                    }
                })
                .build();
        cardViewNative.setCard(card);
        /**/
        return convertView;
    }
}
