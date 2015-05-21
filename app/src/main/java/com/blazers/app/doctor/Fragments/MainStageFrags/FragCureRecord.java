package com.blazers.app.doctor.Fragments.MainStageFrags;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;

import com.blazers.app.doctor.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by liang on 2015/5/7.
 */
public class FragCureRecord extends Fragment {

    private View root;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_cure_record, container, false);
            initTestCards();
        }
        return root;
    }

    void initTestCards() {
        CardViewNative card1 = (CardViewNative) root.findViewById(R.id.carddemo);
        CardViewNative card2 = (CardViewNative) root.findViewById(R.id.carddemo1);
        /* Card1 */
        CardHeader ch1 = new CardHeader(getActivity());
        ch1.setTitle("心脏搭桥手术 - 2013年5月12日");
        ch1.setButtonExpandVisible(true);

        CardExpand ce1 = new CardExpand(getActivity());
        ce1.setTitle("手术详细信息");

        Card c1 = new Card(getActivity(), R.layout.t_built_in_cure_record);
        c1.setTitle("上海市第八人民医院");


        c1.addCardHeader(ch1);
        c1.addCardExpand(ce1);
        card1.setCard(c1);

        /* Card2 */
        CardHeader ch2 = new CardHeader(getActivity());
        ch2.setTitle("冠心病手术 - 2010年12月12日");
        Card c2 = new Card(getActivity(), R.layout.t_built_in_2);
        c2.setTitle("上海市第八人民医院");
        c2.addCardHeader(ch1);
        card2.setCard(c2);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
