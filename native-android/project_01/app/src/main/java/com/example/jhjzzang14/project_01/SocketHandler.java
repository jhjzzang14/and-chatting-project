package com.example.jhjzzang14.project_01;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by jhjzzang14 on 2015-12-05.
 */
public class SocketHandler implements Parcelable{
    private final static String Host = "172.16.42.129";
    private final static int port = 8000;

    private static SocketHandler sHandler;

    private Socket socket;

    public SocketHandler()
    {
        try {
            socket = new Socket(Host, port);
        }catch (IOException e){}
        sHandler = this;
    }

    public int describeContents() { return 0; }
    public void writeToParcel( Parcel dest, int flags ) {}
    public void readFromParcel( Parcel in) {}
    public static final Parcelable.Creator<SocketHandler> CREATOR = new Parcelable.Creator<SocketHandler>()
    {
        public SocketHandler createFromParcel( Parcel in) { return sHandler; }
        public SocketHandler[] newArray(int size) {
            return new SocketHandler[size];
        }
    };

    public Socket getSocket() {
        return socket;
    }
}
