package com.example.todoapplication.UI;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todoapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoDetailFragment extends Fragment {



    private TextView todo_title;
    private TextView todo_date;
    private TextView todo_time;
    private TextView todo_priority;
    private ConstraintLayout constraintLayout;


    // private OnFragmentInteractionListener mListener;

    public TodoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodoDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodoDetailFragment newInstance(String param1, String param2) {
        TodoDetailFragment fragment = new TodoDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);
        todo_title = view.findViewById(R.id.todo_title);
        todo_date = view.findViewById(R.id.todo_detail_date);
        todo_time = view.findViewById(R.id.todo_detail_time);
        todo_priority = view.findViewById(R.id.todo_detail_priority);

        constraintLayout = view.findViewById(R.id.view_pager_layout);
        String priority = getArguments().getString("todo_priority");


        todo_title.setText(getArguments().getString("todo_title"));
        todo_date.setText(getArguments().getString("todo_date"));
        todo_time.setText(getArguments().getString("todo_time"));
        todo_priority.setText(getArguments().getString("todo_priority"));

        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}