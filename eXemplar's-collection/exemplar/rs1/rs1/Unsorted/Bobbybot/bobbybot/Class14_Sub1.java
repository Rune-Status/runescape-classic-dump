// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) nonlb 

import java.io.*;
import java.net.Socket;

public class Class14_Sub1 extends Class14
    implements Runnable {

    public Class14_Sub1(Socket socket, Applet_Sub1 applet_sub1) throws IOException {
        aBoolean645 = false;
        aBoolean649 = true;
        aSocket644 = socket;
        anInputStream642 = socket.getInputStream();
        anOutputStream643 = socket.getOutputStream();
        aBoolean649 = false;
        applet_sub1.method20(this);
    }

    public void method328() {
        super.method328();
        aBoolean645 = true;
        try {
            if(anInputStream642 != null)
                anInputStream642.close();
            if(anOutputStream643 != null)
                anOutputStream643.close();
            if(aSocket644 != null)
                aSocket644.close();
        }
        catch(IOException _ex) {
            System.out.println("Error closing stream");
        }
        aBoolean649 = true;
        synchronized(this) {
            notify();
        }
        aByteArray646 = null;
    }

    public int method349() throws IOException {
        if(aBoolean645)
            return 0;
        else
            return anInputStream642.read();
    }

    public int method340() throws IOException {
        if(aBoolean645)
            return 0;
        else
            return anInputStream642.available();
    }

    public void method339(int i, int j, byte abyte0[]) throws IOException {
        if(aBoolean645)
            return;
        int k = 0;
        boolean flag = false;
        int l;
        for(; k < i; k += l)
            if((l = anInputStream642.read(abyte0, k + j, i - k)) <= 0)
                throw new IOException("EOF");

    }

    public void method347(byte abyte0[], int i, int j) throws IOException {
        if(aBoolean645)
            return;
        if(aByteArray646 == null)
            aByteArray646 = new byte[5000];
        synchronized(this) {
            for(int k = 0; k < j; k++) {
                aByteArray646[anInt648] = abyte0[k + i];
                anInt648 = (anInt648 + 1) % 5000;
                if(anInt648 == (anInt647 + 4900) % 5000)
                    throw new IOException("buffer overflow");
            }

            notify();
        }
    }

    public void run() {
        while(!aBoolean649)  {
            int i;
            int j;
            synchronized(this) {
                if(anInt648 == anInt647)
                    try {
                        wait();
                    }
                    catch(InterruptedException _ex) { }
                if(aBoolean649)
                    return;
                j = anInt647;
                if(anInt648 >= anInt647)
                    i = anInt648 - anInt647;
                else
                    i = 5000 - anInt647;
            }
            if(i > 0) {
                try {
                    anOutputStream643.write(aByteArray646, j, i);
                }
                catch(IOException ioexception) {
                    super.aBoolean552 = true;
                    super.aString548 = "Twriter:" + ioexception;
                }
                anInt647 = (anInt647 + i) % 5000;
                try {
                    if(anInt648 == anInt647)
                        anOutputStream643.flush();
                }
                catch(IOException ioexception1) {
                    super.aBoolean552 = true;
                    super.aString548 = "Twriter:" + ioexception1;
                }
            }
        }
    }

    public InputStream anInputStream642;
    public OutputStream anOutputStream643;
    public Socket aSocket644;
    public boolean aBoolean645;
    public byte aByteArray646[];
    public int anInt647;
    public int anInt648;
    public boolean aBoolean649;
}
