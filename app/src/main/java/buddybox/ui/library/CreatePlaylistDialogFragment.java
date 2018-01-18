package buddybox.ui.library;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.adalbertosoares.buddybox.R;

import buddybox.api.CreatePlaylist;

import static buddybox.CoreSingleton.dispatch;

public class CreatePlaylistDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_playlist, null);
        final EditText edit = (EditText)view.findViewById(R.id.playlistName);

        builder.setView(view)
                .setTitle("New Playlist")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dispatch(new CreatePlaylist(edit.getText().toString(), getArguments().getString("songId")));
                        System.out.println("@@@ CREATE playlist: " + edit.getText());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreatePlaylistDialogFragment.this.getDialog().cancel();
                        System.out.println("@@@ CANCEL new playlist");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
}