package showdraperInformation.presenter;

public interface IAllDrapersInformationPresenter {

    void autoGetAllInformation();

    void setWait(boolean wait);

    void extended(Integer id);
    void stop(Integer id);
    void retracted(Integer id);
}
