package update.presenter;

import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.unconfirmed.UnconfirmedRequestService;
import listener.UpgradeListener;
import util.MyLocalDevice;

/**
 * Created by lenovo on 2017/9/28.
 */
public class SendSingle {

    UpgradeListener listener;
    UpdatePresenter updatePresenter;
    boolean isReceived = false;

    public SendSingle(UpdatePresenter updatePresenter, UpgradeListener listener) {
        this.updatePresenter=updatePresenter;
        this.listener = listener;
    }

    public void send(final UnconfirmedRequestService serviceRequest) {
        listener.setListener(new UpgradeListener.ReceivedListener() {
            @Override
            public void received() {
                isReceived = true;
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    if (!isReceived) {
                        try {
                            MyLocalDevice.getInstance().sendGlobalBroadcast(serviceRequest);
                            System.out.println("WhoIsRequest");
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BACnetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
                if(!isReceived){
                    updatePresenter.cancelUpgrade();
                }
            }
        };
        new Thread(runnable).start();

    }
}
