package PlayerAPI.Impl;

import PlayerAPI.Overrides.PlayerAPI;

import java.util.List;
import java.util.Map;

public interface TelegramImpl {

    Map<String, Object> getTelegramData();

    List<Object> getMail();

    void sendTelegram(Map<String, Object> to, String Subject, String content);

    void deleteTelegram(int id);

}
