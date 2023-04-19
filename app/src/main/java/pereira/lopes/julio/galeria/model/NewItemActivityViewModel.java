package pereira.lopes.julio.galeria.model;

import android.net.Uri;
import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {

    Uri selectPhotoLocation = null;

    public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
    }

    public void selectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
