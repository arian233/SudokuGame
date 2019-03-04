package com.cmpt276.lota.sudoku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WordsListsFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private WordsAdapter mAdapter;
    private static WordListLab wordListLab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_words_list, container, false);
        setRetainInstance(true);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.words_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        wordListLab = WordListLab.get(getActivity());
        String[] lan3 = new String[] {"guitar", "sing","swim","dance", "draw","chess","speak", "join","club","story","write","show","kungfu","drum","violin","piano"};
        String[] lan4 = new String[] {"吉他", "唱歌","游泳","跳舞", "画","国际象棋","说话", "加入","社团","故事","写字","展示","功夫","鼓","小提琴","钢琴"};
        wordListLab.addListsOfWords(new ListsOfWords(lan3,lan4, "chapter3"));
        List<ListsOfWords> listsOfWord = wordListLab.getListsOfWord();

        mAdapter = new WordsAdapter(listsOfWord);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class WordsHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ListsOfWords mListsOfWord;
        private TextView mTitleTextView;

        public WordsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_wordslist, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.words_title);

        }

        public void bind(ListsOfWords listsOfWords) {
            mListsOfWord = listsOfWords;
            mTitleTextView.setText(mListsOfWord.getWordListsName());

        }

        @Override
        public void onClick(View view) {
            wordListLab.setId(mListsOfWord.getId());
            Toast.makeText(getActivity(),
                    mListsOfWord.getWordListsName() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class WordsAdapter extends RecyclerView.Adapter<WordsHolder> {

        private List<ListsOfWords> mListsOfWord;

        public WordsAdapter(List<ListsOfWords> listsOfWord) {
            mListsOfWord = listsOfWord;
        }

        @Override
        public WordsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WordsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WordsHolder holder, int position) {
            ListsOfWords listsOfWord = mListsOfWord.get(position);
            holder.bind(listsOfWord);
        }

        @Override
        public int getItemCount() {
            return mListsOfWord.size();
        }
    }

}
