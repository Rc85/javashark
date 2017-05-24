package PcapApp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by roger85 on 4/12/2017.
 */
public class Info {

    private final SimpleIntegerProperty frame;
    private final SimpleStringProperty source;
    private final SimpleStringProperty dest;
    private final SimpleStringProperty time;
    private final SimpleIntegerProperty frameLen;
    private final SimpleIntegerProperty packetLen;
    private final SimpleIntegerProperty payloadLen;
    private final SimpleStringProperty protocol;
    private final SimpleStringProperty seq;
    private final SimpleStringProperty ack;
    private final SimpleStringProperty windowSize;

    public Info(Integer frame, String source, String dest, String time, Integer frameLen, Integer packetLen, Integer payloadLen, String protocol, String seq, String ack, String windowSize) {
        this.frame = new SimpleIntegerProperty(frame);
        this.source = new SimpleStringProperty(source);
        this.dest = new SimpleStringProperty(dest);
        this.time = new SimpleStringProperty(time);
        this.frameLen = new SimpleIntegerProperty(frameLen);
        this.packetLen = new SimpleIntegerProperty(packetLen);
        this.payloadLen = new SimpleIntegerProperty(payloadLen);
        this.protocol = new SimpleStringProperty(protocol);
        this.seq = new SimpleStringProperty(seq);
        this.ack = new SimpleStringProperty(ack);
        this.windowSize = new SimpleStringProperty(windowSize);
    }

    public void setFrame(Integer fr) {
        frame.set(fr);
    }

    public Integer getFrame() {
        return frame.get();
    }

    public void setSource(String s) {
        source.set(s);
    }

    public String getSource() {
        return source.get();
    }

    public void setDest(String d) {
        dest.set(d);
    }

    public String getDest() {
        return dest.get();
    }

    public void setTime(String t) {
        time.set(t);
    }

    public String getTime() {
        return time.get();
    }

    public void setFrameLen(Integer fl) {
        frameLen.set(fl);
    }

    public Integer getFrameLen() {
        return frameLen.get();
    }

    public void setPacketLen(Integer pl) {
        packetLen.set(pl);
    }

    public Integer getPacketLen() {
        return packetLen.get();
    }

    public void setPayloadLen(Integer pay) {
        payloadLen.set(pay);
    }

    public Integer getPayloadLen() {
        return payloadLen.get();
    }

    public void setProtocol(String pr) {
        protocol.set(pr);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public void setSeq(String sq) {
        seq.set(sq);
    }

    public String getSeq() {
        return seq.get();
    }

    public void setAck(String a) {
        ack.set(a);
    }

    public String getAck() {
        return ack.get();
    }

    public void setWindowSize(String w) {
        windowSize.set(w);
    }

    public String getWindowSize() {
        return windowSize.get();
    }
}
