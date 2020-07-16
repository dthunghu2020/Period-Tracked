package com.hungdt.periodtracked.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hungdt.periodtracked.R;
import com.hungdt.periodtracked.model.Paper;
import com.hungdt.periodtracked.utils.KEY;

public class DetailPaperActivity  extends AppCompatActivity {
    private TextView txtTitle, txtBody;
    private ImageView imgPaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paper);

        txtTitle=findViewById(R.id.txtTitle);
        txtBody=findViewById(R.id.txtBody);
        imgPaper=findViewById(R.id.imgPaper);

        Intent intent = getIntent();
        Paper paper = (Paper) intent.getSerializableExtra(KEY.PAPER);

        assert paper != null;
        txtTitle.setText(paper.getTitle());
        txtBody.setText(paper.getBody());
        Glide.with(DetailPaperActivity.this)
                .load(paper.getIdImage())
                .into(imgPaper);
    }
}
