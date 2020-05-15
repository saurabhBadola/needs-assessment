package in.sunfox.needs.assesment.change_language;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import in.sunfox.needs.assesment.helper.LocaleHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;

public class ChangeLanguageViewModel extends BaseObservableViewModel {

    private String selectedLanguage = "";
    private boolean isChangingLanguage = false;
    private Application application;

    public ChangeLanguageViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        selectedLanguage = LocaleHelper.getLanguage(application);
//        if (selectedLanguage != null && !selectedLanguage.equals("en"))
//            setSelectedLanguage(LocaleHelper.getLanguage(application));
    }

    public void setSelectedLanguage(String language) {
        selectedLanguage = language;
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                LocaleHelper.setLocale(application, selectedLanguage);
                ((ChangeLanguageActivity) baseActivity).setLanguage();
            }
        });
        notifyPropertyChanged(BR.selectedLanguage);
    }

    public void dispatchContinue() {

        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((ChangeLanguageActivity) baseActivity).openNext();
            }
        });
    }

    public void dispatchGoBack() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                baseActivity.finish();
            }
        });
    }


    @Bindable
    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    @Bindable
    public boolean isChangingLanguage() {
        return isChangingLanguage;
    }

    public void setChangingLanguage(boolean changingLanguage) {
        isChangingLanguage = changingLanguage;
    }
}
