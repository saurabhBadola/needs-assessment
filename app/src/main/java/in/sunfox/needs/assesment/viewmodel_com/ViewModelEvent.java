package in.sunfox.needs.assesment.viewmodel_com;

public abstract class ViewModelEvent {
    private boolean handled = false;

    public void handle(BaseActivity baseActivity) {
        handled = true;
    }

    public boolean isHandled() {
        return handled;
    }

}
