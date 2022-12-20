package com.example.sqllite.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.Model.Faculty;
import com.example.sqllite.Model.FacultyAdapter;
import com.example.sqllite.Model.SwipeItemFaculty;
import com.example.sqllite.R;
import com.example.sqllite.Sqlite.StudentDBHelper;
import com.example.sqllite.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FacultyAdapter adapter;
    private List<Faculty> listFaculty;
    private StudentDBHelper db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new StudentDBHelper(getContext());
        RecyclerView rcvFaculty = view.findViewById(R.id.rcvFaculty);
        listFaculty = db.GetAllFalculty();
        adapter = new FacultyAdapter(listFaculty,db);
        rcvFaculty.setAdapter(adapter);
        rcvFaculty.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvFaculty.addItemDecoration(itemDecoration);
        view.findViewById(R.id.floatingAddFaculty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialogFaculty = LayoutInflater.from(getContext()).inflate(R.layout.dialog_faculty, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(viewDialogFaculty);
                AlertDialog alert = builder.create();
                alert.show();

                EditText dialogMaKhoa = viewDialogFaculty.findViewById(R.id.dialogInputMaKhoa);
                EditText dialogTenKhoa = viewDialogFaculty.findViewById(R.id.dialogInputTenKhoa);
                Button dialogSaveKhoa = viewDialogFaculty.findViewById(R.id.dialogSaveFaculty);

                dialogSaveKhoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Faculty f = new Faculty();
                        f.FacultyId = Integer.parseInt(dialogMaKhoa.getText().toString());
                        f.FacultyName = dialogTenKhoa.getText().toString();

                        if(db.Insert(f) == true){
                            listFaculty.add(f);
                            adapter.notifyItemInserted(listFaculty.size()-1);

                            Toast.makeText(getContext(),"YEAHHHHHHH THEM KHOA",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Khong duoc trung ma  KHOA",Toast.LENGTH_LONG).show();
                        }

                        alert.dismiss();
                    }
                });
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItemFaculty((FacultyAdapter) adapter));
        itemTouchHelper.attachToRecyclerView(rcvFaculty);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}