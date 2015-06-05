package com.blazers.app.doctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.blazers.app.doctor.R;
import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

import java.util.ArrayList;

/**
 * Created by liang on 2015/5/25.
 */
public class IllnessCaseAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context ctx;

    public IllnessCaseAdapter(Context ctx) {
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
            convertView = inflater.inflate(R.layout.item_illness_case, parent, false);
        }
        /**/
        CardViewNative cardViewNative = (CardViewNative) convertView.findViewById(R.id.carddemo_largeimagetext);

        ArrayList<BaseSupplementalAction> actions = new ArrayList<>();

        TextSupplementalAction t1 = new TextSupplementalAction(ctx, R.id.text1);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(ctx, " Click on Text SHARE ", Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t1);

        TextSupplementalAction t2 = new TextSupplementalAction(ctx, R.id.text2);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(ctx," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t2);

        MaterialLargeImageCard card = MaterialLargeImageCard.with(ctx)
                .setTextOverImage("心悸疾病病历")
                .setTitle("心悸病历")
                .setSubTitle("2015年5月25日 添加")
                .useDrawableId(R.drawable.map)
                .setupSupplementalActions(R.layout.card_case_illness_supply, actions )
                .build();

        cardViewNative.setCard(card);

        return convertView;
    }
}
