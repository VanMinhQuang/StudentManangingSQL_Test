package com.example.sqllite.Model;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.R;
import com.example.sqllite.Sqlite.StudentDBHelper;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ViewHolder> {
    List<Faculty> listFaculty;
    StudentDBHelper db;

    public FacultyAdapter(List<Faculty> listFaculty, StudentDBHelper _db) {
        this.listFaculty = listFaculty;
        db = _db;
    }

    @NonNull
    @Override
    public FacultyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_falcuty, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAdapter.ViewHolder holder, int position) {
        Faculty f = listFaculty.get(position);
        holder.textFacultyName.setText(f.FacultyName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialogFaculty = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_faculty,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(viewDialogFaculty.getContext());
                builder.setView(viewDialogFaculty);
                AlertDialog alert = builder.create();
                alert.show();
                EditText dialogInputMaKhoa = viewDialogFaculty.findViewById(R.id.dialogInputMaKhoa);
                EditText dialogInputTenKhoa = viewDialogFaculty.findViewById(R.id.dialogInputTenKhoa);
                dialogInputMaKhoa.setText(String.valueOf(f.FacultyId));
                dialogInputTenKhoa.setText(f.FacultyName);
                dialogInputMaKhoa.setEnabled(false);

                viewDialogFaculty.findViewById(R.id.dialogSaveFaculty).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        f.FacultyName = dialogInputTenKhoa.getText().toString();
                        db.Update(f);
                        notifyItemChanged(holder.getAdapterPosition());
                        Toast.makeText(viewDialogFaculty.getContext(),"OH YEAHHHHHHHH SAVE KHOA",Toast.LENGTH_LONG).show();
                        alert.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFaculty.size();
    }

    public void deleteItem(int pos){
        db.Delete(listFaculty.get(pos));
        listFaculty.remove(pos);

        notifyItemRemoved(pos);
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView textFacultyName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFacultyName = itemView.findViewById(R.id.textFacultyName);
        }
    }
}
