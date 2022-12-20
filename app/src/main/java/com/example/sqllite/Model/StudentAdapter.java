package com.example.sqllite.Model;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.R;
import com.example.sqllite.Sqlite.StudentDBHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

     List<Student> lstStudent;
    StudentDBHelper db;

    public StudentAdapter(List<Student> lstStudent, StudentDBHelper db) {
        this.lstStudent = lstStudent;
        this.db = db;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student s = lstStudent.get(position);
        holder.textStudentID.setText(s.maSV);
        holder.textStudentName.setText(s.tenSV);
        holder.textStudentScore.setText(String.valueOf(s.diemTB));
        holder.textStudentFaculty.setText(s.Khoa);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialogFaculty = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_student,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(viewDialogFaculty.getContext());
                builder.setView(viewDialogFaculty);
                AlertDialog alert = builder.create();
                alert.show();
                EditText dialogInputMaSV = viewDialogFaculty.findViewById(R.id.dialogInputMaSV);
                EditText dialogInputTenSV = viewDialogFaculty.findViewById(R.id.dialogInputTenSV);
                EditText dialogInputDiem = viewDialogFaculty.findViewById(R.id.dialogInputDiemSV);
                Spinner dialogInputKhoa = viewDialogFaculty.findViewById(R.id.dialogInputKhoa);
                dialogInputMaSV.setText(String.valueOf(s.maSV));
                dialogInputTenSV.setText(s.tenSV);
                dialogInputDiem.setText(String.valueOf(s.diemTB));
                ArrayList<String> lstFaculty = db.GetAllNameFalculty();
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(builder.getContext(), android.R.layout.simple_list_item_1, lstFaculty);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialogInputKhoa.setAdapter(adp1);
                int getPosition = adp1.getPosition(s.Khoa);
                dialogInputKhoa.setSelection(getPosition);
                dialogInputMaSV.setEnabled(false);

                viewDialogFaculty.findViewById(R.id.dialogSaveStudent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s.tenSV = dialogInputTenSV.getText().toString();
                        s.diemTB = Float.parseFloat(dialogInputDiem.getText().toString());
                        s.Khoa = dialogInputKhoa.getSelectedItem().toString();
                        db.UpdateStudent(s);
                        notifyItemChanged(holder.getAdapterPosition());
                        Toast.makeText(viewDialogFaculty.getContext(),"OH YEAHHHHHHHH SAVE SV",Toast.LENGTH_LONG).show();
                        alert.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstStudent.size();
    }
    public void deleteItem(int pos){
        db.DeleteStudent(lstStudent.get(pos));
        lstStudent.remove(pos);
        notifyItemRemoved(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textStudentName, textStudentScore, textStudentFaculty, textStudentID;
        LinearLayout layout_student;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_student = itemView.findViewById(R.id.layout_student);
            textStudentID = itemView.findViewById(R.id.textStudentID);
            textStudentName = itemView.findViewById(R.id.textStudentName);
            textStudentScore = itemView.findViewById(R.id.textStudentScore);
            textStudentFaculty = itemView.findViewById(R.id.textStudentFaculty);
        }
    }
}
