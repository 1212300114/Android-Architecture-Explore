package com.jijunjie.androidlibrarysystem.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * @author jijunjie
 * @Description: this the the fragment to add book
 * @date on 16/5/6.
 */
public class FragmentAddBook extends Fragment {

    @Bind(R.id.etBookName)
    EditText etBookName;
    @Bind(R.id.etAuthorName)
    EditText etAuthorName;
    @Bind(R.id.etPress)
    EditText etPress;
    @Bind(R.id.etClassName)
    EditText etClassName;
    @Bind(R.id.etCountry)
    EditText etCountry;
    @Bind(R.id.etCount)
    EditText etCount;
    @Bind(R.id.ivBook)
    ImageView ivBook;


    private static final int REQUEST_CODE_GALLERY = 5;
    private String imagePath = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return createFragmentView(inflater, container);
    }


    private View createFragmentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_add_book, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.flSelectImage)
    public void selectBookImage() {
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                if (null != resultList && resultList.size() > 0) {
                    String path = resultList.get(0).getPhotoPath();
                    if (!TextUtils.isEmpty(path)) {
                        DrawableUtils.displayLocImg(ivBook, path);
                        imagePath = path;
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    @OnClick(R.id.tvAdd)
    public void addBook() {
        String bookName = etBookName.getText().toString().trim();
        String authorName = etAuthorName.getText().toString().trim();
        String press = etPress.getText().toString().trim();
        String className = etClassName.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String count = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(bookName) || TextUtils.isEmpty(authorName) || TextUtils.isEmpty(press) ||
                TextUtils.isEmpty(className) || TextUtils.isEmpty(country) || TextUtils.isEmpty(count)) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "请输入完整的书籍信息", Toast.LENGTH_SHORT).show();
        }

        final Book book = new Book();
        book.setBookName(bookName);
        book.setBookAuthor(authorName);
        book.setBookPress(press);
        book.setClassName(className);
        book.setBookCountry(country);
        book.setBookCount(Integer.valueOf(count));
        book.setBookLeft(Integer.valueOf(count));
        final BmobFile imageFile = new BmobFile(new File(imagePath));
        book.setBookImage(imageFile);

        imageFile.uploadblock(getActivity(), new UploadFileListener() {

            @Override
            public void onSuccess() {
                //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                Toast.makeText(getActivity().getApplicationContext(),
                        "图片上传文件成功", Toast.LENGTH_SHORT)
                        .show();
                book.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "添加成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "添加失败" + s, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "上传失败" + msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
