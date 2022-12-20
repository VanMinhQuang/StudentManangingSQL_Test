package com.example.sqllite.ui.home;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.example.sqllite.Model.Student;
import com.example.sqllite.Model.StudentAdapter;
import com.example.sqllite.Model.SwipeItemFaculty;
import com.example.sqllite.Model.SwipeItemStudent;
import com.example.sqllite.R;
import com.example.sqllite.Sqlite.StudentDBHelper;
import com.example.sqllite.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Student> lstStudent;
    private ArrayList<String> lstFaculty;
    private StudentAdapter adapter;
    private StudentDBHelper db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new StudentDBHelper(getContext());
        RecyclerView rcvStudent = view.findViewById(R.id.rcvStudent);
        lstStudent = db.GetAllStudent();
        adapter = new StudentAdapter(lstStudent,db);
        rcvStudent.setAdapter(adapter);
        rcvStudent.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvStudent.addItemDecoration(itemDecoration);
        lstFaculty = db.GetAllNameFalculty();
        try{

        view.findViewById(R.id.floatdingAddStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialogFaculty = LayoutInflater.from(getContext()).inflate(R.layout.dialog_student, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(viewDialogFaculty);
                AlertDialog alert = builder.create();
                alert.show();

                EditText dialogInputIdStudent = viewDialogFaculty.findViewById(R.id.dialogInputMaSV);
                EditText dialogStudentName = viewDialogFaculty.findViewById(R.id.dialogInputTenSV);
                EditText dialogStudentScore = viewDialogFaculty.findViewById(R.id.dialogInputDiemSV);
                Button button = viewDialogFaculty.findViewById(R.id.dialogSaveStudent);
                Spinner dialogKhoa = viewDialogFaculty.findViewById(R.id.dialogInputKhoa);


                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lstFaculty);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialogKhoa.setAdapter(adp1);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Student f = new Student();
                            f.maSV = dialogInputIdStudent.getText().toString();
                            f.tenSV = dialogStudentName.getText().toString();
                            f.diemTB = Float.parseFloat(dialogStudentScore.getText().toString());
                            f.Khoa = dialogKhoa.getSelectedItem().toString();

                            if(f.diemTB > 10 || f.diemTB < 0){
                                Toast.makeText(getContext(),"Diem khong hop le",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(db.InsertStudent(f) == true){
                                lstStudent.add(f);
                                adapter.notifyItemInserted(lstStudent.size()-1);
                                Toast.makeText(getContext(),"YEAHHHHHHH THEM SINH VIEN",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(),"Khong duoc trung MSSV",Toast.LENGTH_LONG).show();
                            }


                            alert.dismiss();
                        }
                    });

            }
        });
        }catch(Exception ex){
            Toast.makeText(getContext(),"Khong duoc trung id",Toast.LENGTH_LONG).show();
            return;
        }
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItemStudent(adapter));
        itemTouchHelper.attachToRecyclerView(rcvStudent);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}