/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import com.mycompany.chatserver.ServerChat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trung
 */
public class Server extends Observable{
    int mPort = 1106;
    ServerSocket mServerSocket;
    Thread mThreadAccept, mThreadProcess;
 public Server(Observer obs)  //hàm khởi tạo khi chưa có socket
    {
        this.addObserver(obs);
    }
   
    public Server(ServerSocket serverSocket, Observer obs)   //hàm khởi tạo khi đã có socket
    {
        this.addObserver(obs);
        mServerSocket = serverSocket;
    }
    
    public void Dispose() throws IOException
    {
        if(mThreadAccept!=null)
        {
            mThreadAccept.stop();
            mThreadProcess.stop();
            mServerSocket.close(); 
        }
    }
    
    public boolean StartServer() //khởi động server
            
    {
        try 
        {
            mServerSocket = new ServerSocket(mPort);
            StartThreadAccept();
           
            notifyObservers("Khởi động server thành công");
            return true;
        } catch (IOException ex) 
        {
            notifyObservers("Không thể khởi động server");
            return false;
        }
    }
    void StartThreadAccept()   //bắt đầu luồng chấp nhận các socket kết nối đến
    {
        mThreadAccept = new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    while(true)
                    {
                        Socket socket = mServerSocket.accept();
                       
                     
                        
                    }
                }catch (IOException ex) {
                    notifyObservers("Lỗi kết nối");
                }
            }
        });
        mThreadAccept.start();
    }
    
    
    
    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
    
   
  
           
}
