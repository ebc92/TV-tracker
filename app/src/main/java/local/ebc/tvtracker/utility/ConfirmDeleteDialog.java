package local.ebc.tvtracker.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import local.ebc.tvtracker.R;

/**
 * Created by ebc on 25.10.2016.
 */

    public class ConfirmDeleteDialog extends DialogFragment {
        /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
        // Use this instance of the interface to deliver action events
        ConfirmDeleteDialogListener mListener;
        // Override the Fragment.onAttach() method to instantiate the ConfirmDeleteDialogListener
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            // Verify that the host activity implements the callback interface
            try {
                // Instantiate the ConfirmDeleteDialogListener so we can send events to the host
                mListener = (ConfirmDeleteDialogListener) activity;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString()
                        + " must implement ConfirmDeleteDialogListener");
            }
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String message = getArguments().getString("message");
            String positiveButton = getArguments().getString("positiveButton");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // Activate method onDialogPositiveClick inside implementing class
                            mListener.onDialogPositiveClick(ConfirmDeleteDialog.this);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // Activate method onDialogNegativeClick inside implementing class
                            mListener.onDialogNegativeClick(ConfirmDeleteDialog.this);
                        }
                    });
            return builder.create();
        }
        public interface ConfirmDeleteDialogListener {
            void onDialogPositiveClick(DialogFragment dialog);
            void onDialogNegativeClick(DialogFragment dialog);
        }
    }
