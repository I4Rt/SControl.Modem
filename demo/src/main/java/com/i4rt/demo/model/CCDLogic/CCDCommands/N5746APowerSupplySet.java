package com.i4rt.demo.model.CCDLogic.CCDCommands;

import com.i4rt.demo.model.ReceiverTwoChannels;
import com.i4rt.demo.model.CCDLogic.CCD;
import com.i4rt.demo.model.DataBin;
import com.i4rt.demo.model.algs.FixPoint;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.nio.ByteBuffer;

@NoArgsConstructor
public class N5746APowerSupplySet implements CCDCommand {

    private String rowDataJsonString;



    public static String sendData(String[] params) {

        


        Boolean mode = (params[2]).equals("1");

        String dataStr = "AA11" + Hex.encodeHexString(FixPoint.getFixPointDataFromStr(params[0])) + Hex.encodeHexString(FixPoint.getFixPointDataFromStr(params[1])) + (mode ? "01":"00") + "55".repeat(1005);

        byte[] data = DataBin.convertDataFromHexStrToByteArray(dataStr);

        System.out.println("len: " + Hex.encodeHexString(data).length());
        System.out.println("str: " + Hex.encodeHexString(data));

        ReceiverTwoChannels receiverTwoChannels = new ReceiverTwoChannels(CCD.getIP(), CCD.getSendPort(), CCD.getReceivePort(), 5000);


        try {
            byte[] answer = receiverTwoChannels.sendData(data);

            return "<div>Запрос отправлен. Команда: " + Hex.encodeHexString(data).substring(0, 127) + "</div>" + ((answer != null) ? ((Hex.encodeHexString(answer).length() > 128) ?  Hex.encodeHexString(answer).substring(0, 127): Hex.encodeHexString(answer)) : ("<div class = \"row\" style = \"width: fit-content; align-items: baseline\"><b style=\"color: #b8a500; font-weight: bold\">WARNING</b>: Ответ не получен.<br></div>"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "<div class = \"row\" style = \"width: fit-content; align-items: baseline\"><b style=\"color: red; font-weight: bold\">ERROR</b>: Не удалось установить значение параметров питания N5746A<br></div>";
    }
}
