package PlayerAPI.Impl;

public interface NetWorthImpl {

    String getUuid();

    int getMoney();

    void setMoney(int amount);

    void addMoney(int amount);

    void subtractMoney(int amount);

}
