/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author trung
 */
public class Client extends Observable{
    
    String serverName = "localhost";
    int port = 1106;
    Socket mSocket;
    BufferedWriter mBufferWriter;
    DataInputStream mDataInputStream;
    Thread mThread;
    
    public Client(Observer obs)  //hàm khởi tạo khi chưa có socket
    {
        this.addObserver(obs);
    }
    public Client(Socket socket, Observer obs)   //hàm khởi tạo khi đã có socket
    {
        this.addObserver(obs);
        mSocket = socket;
    }
    public void Dispose()
    {
        if(mSocket!=null)
        {
            try 
            {
                mSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(mThread!=null)
            mThread.stop();
    }
    public boolean StartConnect()
    {
        try 
        {
            mSocket = new Socket(serverName, port); 
            //Sử dụng dataInputStream để đợi nhận kết quả thì vòng while sẽ ko cần chạy liên tục -> tránh tốn hiệu suất
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mBufferWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF8"));
//            StartThreadWaitResult();
            return true;
        } catch (IOException ex) 
        {
            Result result = new Result("", ResultCode.ERROR, "Không thể kết nối đến server");
            notifyObservers(result);
            return false;
        }
    }
    
  
    
    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
 
   
   
    
}
