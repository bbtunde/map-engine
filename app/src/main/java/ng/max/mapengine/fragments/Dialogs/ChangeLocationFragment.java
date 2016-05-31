package ng.max.mapengine.fragments.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ng.max.mapengine.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangeLocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangeLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ChangeLocationFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match


    // TODO: Rename and change types of parameters

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AcceptItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeLocationFragment newInstance() {
        ChangeLocationFragment fragment = new ChangeLocationFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ChangeLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_location, container, false);
        final EditText location = (EditText) v.findViewById(R.id.location);
        Button btn_change_location = (Button) v.findViewById(R.id.btn_change_location);

        btn_change_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    };
}
