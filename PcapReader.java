package PcapApp;

import java.util.ArrayList;

/**
 * Created by roger85 on 3/23/2017.
 */
public class PcapReader {

    // Data Link Layer
    ArrayList<String> arrivalTime = new ArrayList<String>();
    ArrayList<Integer> frameLength = new ArrayList<Integer>();

    // Network Layer
    ArrayList<String> numOfProtocols = new ArrayList<String>();
    ArrayList<String> destinationIP = new ArrayList<String>();
    ArrayList<String> sourceIP = new ArrayList<String>();
    ArrayList<Integer> ipPacketLength = new ArrayList<Integer>();

    // Transport Layer
    ArrayList<String> destinationPorts = new ArrayList<String>();
    ArrayList<String> sourcePorts = new ArrayList<String>();
    ArrayList<Integer> payloadLength = new ArrayList<Integer>();
    ArrayList<String> windowSize = new ArrayList<String>();
    ArrayList<Integer> tcpPacketLength =  new ArrayList<Integer>();
    ArrayList<Integer> udpPacketLength = new ArrayList<Integer>();
    ArrayList<String> seqNum = new ArrayList();
    ArrayList<String> ackNum = new ArrayList();
    Integer numUDP = 0;
    Integer numTCP = 0;
    Integer numARP = 0;
    Integer numICMP = 0;
    Integer numConnection = 0;

    public PcapReader() {

    }

    public Integer getARP() {
        return numARP;
    }

    public Integer getICMP() {
        return numICMP;
    }

    public void setUDP(Integer n) {
        this.numUDP = n;
    }

    public void setTCP(Integer n) {
        this.numTCP = n;
    }

    public void setARP(Integer n) {
        this.numARP = n;
    }

    public void setICMP(Integer n) {
        this.numICMP = n;
    }

    public void setConn(Integer n) {
        this.numConnection = n;
    }

    public void incrementICMP() {
        numICMP++;
    }

    public void incrementARP() {
        numARP++;
    }

    public void addDestPorts(String dp) {
        destinationPorts.add(dp);
    }

    public void addSourcePorts(String sp) {
        sourcePorts.add(sp);
    }

    public ArrayList getProtocols() {
        return numOfProtocols;
    }

    public void incrementTCP() {
        numTCP++;
    }

    public Integer getTCPConn() {
        return numTCP;
    }

    public Integer getUDPConn() {
        return numUDP;
    }

    public void incrementUDP() {
        numUDP++;
    }

    public ArrayList getWindowSize() {
        return windowSize;
    }

    public void incrementConn() {
        numConnection++;
    }

    public Integer getTotalConn() {
        return numConnection;
    }

    public void addPayloadLen(Integer pl) {
        payloadLength.add(pl);
    }

    public ArrayList getPayloadLength() {
        return payloadLength;
    }

    public void addWindowSize(String ws) {
        windowSize.add(ws);
    }

    public void addDestIP(String address) {
        destinationIP.add(address);
    }

    public void addSourceIP(String address) {
        sourceIP.add(address);
    }

    public String[] getDestIP() {
        String[] dIP = new String[destinationIP.size()];

        for (int i = 0; i < destinationIP.size(); i++) {
            dIP[i] = destinationIP.get(i);
        }

        return dIP;
    }

    public String[] getSourceIP() {
        String[] sIP = new String[sourceIP.size()];

        for (int i = 0; i < sourceIP.size(); i++) {
            sIP[i] = sourceIP.get(i);
        }

        return sIP;
    }

    public void addIPPacketLen(Integer ipLen) {
        ipPacketLength.add(ipLen);
    }

    public ArrayList getPacketLen() {
        return ipPacketLength;
    }

    public void addFrameLen(Integer len) {
        frameLength.add(len);
    }

    public ArrayList getFrameLen() {
        return frameLength;
    }

    public void addProtocols(String p) {
        numOfProtocols.add(p);
    }

    public void addTime(String time) {
        arrivalTime.add(time);
    }

    public ArrayList getTime() {
        return arrivalTime;
    }

    public void addSeq(String seq) {
        seqNum.add(seq);
    }

    public void addAck(String ack) {
        ackNum.add(ack);
    }

    public ArrayList getSeq() {
        return seqNum;
    }

    public ArrayList getAck() {
        return ackNum;
    }
}
