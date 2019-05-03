package com.oleksandr.havryliuk.editor.new_edit_post.new_post;

import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.new_edit_post.validator.IValidateView;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class NewPostView implements NewPostContract.INewPostView, View.OnClickListener, IValidateView {

    private NewPostContract.INewPostPresenter presenter;
    private Spinner spinner;
    private EditText titleEditText, aboutEditText, textEditText;
    private TextView durationTextView, doneButton, imageErrorTextView;
    private SeekBar durationSeekBar;
    private Switch stateSwitch;
    private ImageView addImageView;
    private LinearLayout addImageLayout, addTextLayout;
    private View root;

    @Override
    public void setPresenter(NewPostContract.INewPostPresenter presenter) {
        this.presenter = presenter;
        presenter.setTypeClick(NEWS);
    }

    @Override
    public void init(final View root) {
        this.root = root;

        initView();
        initListener();
    }

    private void initView() {
        spinner = root.findViewById(R.id.spinner);
        titleEditText = root.findViewById(R.id.title_edit_text);
        aboutEditText = root.findViewById(R.id.about_edit_text);
        textEditText = root.findViewById(R.id.text_edit_text);
        durationTextView = root.findViewById(R.id.duration_value_text_view);
        durationSeekBar = root.findViewById(R.id.duration_seek_bar);
        stateSwitch = root.findViewById(R.id.state_switch);
        addImageView = root.findViewById(R.id.add_image_view);
        doneButton = root.findViewById(R.id.done_button);
        addImageLayout = root.findViewById(R.id.add_image_layout);
        addTextLayout = root.findViewById(R.id.add_text_layout);
        imageErrorTextView = root.findViewById(R.id.image_error_text_view);
    }

    private void initListener(){
        spinner.setAdapter(ArrayAdapter.createFromResource(root.getContext(),
                R.array.type_list, R.layout.item_type));
        spinner.setOnItemSelectedListener(new SpinnerListener());

        doneButton.setOnClickListener(this);
        addImageView.setOnClickListener(this);

        durationSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
    }

    @Override
    public void setImage(Uri uri) {
        addImageView.setImageURI(uri);
    }

    @Override
    public void hideAddImageLayout() {
        addImageLayout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public void hideAddTextLayout() {
        addTextLayout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public void showAddImageLayout() { addImageLayout.setVisibility(View.VISIBLE); }

    @Override
    public void showAddTextLayout() { addTextLayout.setVisibility(View.VISIBLE); }

    @Override
    public void showTitleError() {
        titleEditText.setError("Please enter title");
    }

    @Override
    public void showImageError() {
        imageErrorTextView.setText("Please pick image");
    }

    @Override
    public void showTextError() {
        textEditText.setError("Please enter text");
    }

    @Override
    public void hideTitleError() {
        titleEditText.setError(null);
    }

    @Override
    public void hideImageError() {
        imageErrorTextView.setText(null);
    }

    @Override
    public void hideTextError() {
        textEditText.setError(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_button:
                presenter.doneClick(
                        titleEditText.getText().toString(),
                        aboutEditText.getText().toString(),
                        textEditText.getText().toString(),
                        Integer.parseInt(durationTextView.getText().toString()),
                        stateSwitch.hasTransientState());
                break;
            case R.id.add_image_view:
                presenter.setImageClick();
                break;
        }
    }

    class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            durationTextView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] types = root.getContext().getResources().getStringArray(R.array.type_list);
            presenter.setTypeClick(types[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
