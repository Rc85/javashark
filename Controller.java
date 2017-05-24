package PcapApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.jnetpcap.util.PcapPacketArrayList;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by roger85 on 3/22/2017.
 */
public class Controller implements Initializable {

    private String filePath;
    PcapReader reader = new PcapReader();

    @FXML public BorderPane mainPane;
    //public TextField fileInputField;
    @FXML public MenuItem arrivalButton, frameButton, payloadButton, connectionButton, closeButton;
    //public TabPane dataLeftPane;
    @FXML public ScrollPane centerPane;
    @FXML public TableColumn <Info, Integer> numCol;
    @FXML public TableColumn <Info, String> sourceCol;
    @FXML public TableColumn <Info, String> destCol;
    @FXML public TableColumn <Info, String> timeCol;
    @FXML public TableColumn <Info, Integer> frameCol;
    @FXML public TableColumn <Info, Integer> packetCol;
    @FXML public TableColumn <Info, Integer> payloadCol;
    @FXML public TableColumn <Info, String> seqCol;
    @FXML public TableColumn <Info, String> ackCol;
    @FXML public TableColumn <Info, String> winSizeCol;
    @FXML public TableColumn <Info, String> protocolCol;
    @FXML public TableView<Info> table;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //assert fileInputField != null : "fx:id=\"fileInputField\" was not injected.";
        //assert doneButton != null : "fx:id=\"doneButton\" was not injected.";
        //assert dataLeftPane != null : "fx:id\"dataLeftPane\" was not injected";
        assert arrivalButton != null : "fx:id\"arrivalButton\" was not injected";
        assert frameButton != null : "fx:id\"frameButton\" was not injected";
        assert payloadButton != null : "fx:id\"payloadButton\" was not injected";
        assert connectionButton != null : "fx:id\"connectionButton\" was not injected";
        assert centerPane != null : "fx:id\"centerPane\" was not injected";
        assert table != null : "fx:id\"table\" was not injected";
        //assert allIP != null : "fx:id\"allIP\" was not injected";
        //assert destIP != null : "fx:id\"destIP\" was not injected";
        //assert sourceIP != null : "fx:id\"sourceIP\" was not injected";
        assert numCol != null : "fx:id\"numCol\" was not injected";
        assert sourceCol != null : "fx:id\"sourceCol\" was not injected";
        assert destCol != null : "fx:id\"destCol\" was not injected";
        assert timeCol != null : "fx:id\"timeCol\" was not injected";
        assert frameCol != null : "fx:id\"frameCol\" was not injected";
        assert packetCol != null : "fx:id\"packetCol\" was not injected";
        assert payloadCol != null : "fx:id\"payloadCol\" was not injected";
        assert protocolCol != null : "fx:id\"protocolCol\" was not injected";
        assert seqCol != null : "fx:id\"seqCol\" was not injected";
        assert ackCol != null : "fx:id\"ackCol\" was not injected";
        assert winSizeCol != null : "fx:id\"winSizeCol\" was not injected";

        numCol.setCellValueFactory(new PropertyValueFactory<Info, Integer>("frame"));
        sourceCol.setCellValueFactory(new PropertyValueFactory<Info, String>("source"));
        destCol.setCellValueFactory(new PropertyValueFactory<Info, String>("dest"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Info, String>("time"));
        frameCol.setCellValueFactory(new PropertyValueFactory<Info, Integer>("frameLen"));
        packetCol.setCellValueFactory(new PropertyValueFactory<Info, Integer>("packetLen"));
        payloadCol.setCellValueFactory(new PropertyValueFactory<Info, Integer>("payloadLen"));
        protocolCol.setCellValueFactory(new PropertyValueFactory<Info, String>("protocol"));
        seqCol.setCellValueFactory(new PropertyValueFactory<Info, String>("seq"));
        ackCol.setCellValueFactory(new PropertyValueFactory<Info, String>("ack"));
        winSizeCol.setCellValueFactory(new PropertyValueFactory<Info, String>("windowSize"));

        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        table.setPrefSize(centerPane.getWidth(), centerPane.getHeight());

        //dataLeftPane.setExpandedPane(allIPBox);
    }

    public void setFilePath() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        String filePathString = "";

        if (selectedFile != null) {
            filePathString = selectedFile.getPath().toString();
            String fileName = filePathString.replace("\\", "\\\\");
            this.filePath = fileName;

            Processor();
        }

        populateTable();
    }

    public void populateTable() {
        String[] source = reader.getSourceIP();
        String[] dest = reader.getDestIP();
        ArrayList<String> time = reader.getTime();
        ArrayList<Integer> frameLen = reader.getFrameLen();
        ArrayList<Integer> packetLen = reader.getPacketLen();
        ArrayList<Integer> payloadLen = reader.getPayloadLength();
        ArrayList<String> protocol = reader.getProtocols();
        ArrayList<String> seq = reader.getSeq();
        ArrayList<String> ack = reader.getAck();
        ArrayList<String> ws = reader.getWindowSize();

        ObservableList<Info> data = FXCollections.observableArrayList();

        /*System.out.println("s " + source.length);
        System.out.println("d " + dest.length);
        System.out.println("t " + time.size());
        System.out.println("f " + frameLen.size());
        System.out.println("p " + packetLen.size());
        System.out.println("pl " + payloadLen.size());
        System.out.println("pr " + protocol.size());
        System.out.println("sq " + seq.size());
        System.out.println("a " + ack.size());
        System.out.println("ws " + ws.size());*/

        for (int i = 0; i < protocol.size(); i++) {
            Integer fl = frameLen.get(i);
            String s = source[i];
            String d = dest[i];
            String t = time.get(i);
            Integer pl = packetLen.get(i);
            Integer pyl = payloadLen.get(i);
            String se = seq.get(i);
            String a = ack.get(i);
            String w = ws.get(i);
            String pr = protocol.get(i);;

            data.add(new Info(i+1, s, d, t, fl, pl, pyl, pr, se, a, w));
            //System.out.println(t);
        }

        table.getItems().setAll(data);

        //data.add((new Info(1, "test", "test", "test", 1, 1, 1, "test", 1, 1, 1)));
        //table.getItems().setAll(data);

        //table.getItems().add(new Info(1, "test", "test", "test", 1, 1, 1, "test", 1, 1, 1));

    }

    public void retrieveTime() {

        TreeMap<String, Integer> uniqueTime = new TreeMap<String, Integer>();
        ArrayList<String> key = reader.getTime();

        for (int i = 0; i < key.size(); i++) {
            if (uniqueTime.containsKey(key.get(i))) {
                uniqueTime.put(key.get(i), uniqueTime.get(key.get(i)) + 1);
            } else {
                uniqueTime.put(key.get(i), 1);
            }
        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (mm:ss:sss");
        yAxis.setLabel("Number of Packets");
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Packet Count at Specified Time");
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<String, Integer> rt: uniqueTime.entrySet()) {
            String k = rt.getKey();
            Integer v = rt.getValue();

            series.getData().add(new XYChart.Data(k,v));
        }

        barChart.getData().add(series);
        barChart.setPrefSize(centerPane.getWidth(), centerPane.getHeight());

        centerPane.setContent(barChart);
    }

    public void mainScene() {
        centerPane.setContent(table);
    }

    public void closeApp() {
        Stage stage = (Stage) centerPane.getScene().getWindow();
        stage.close();
    }

    public void frameLength() {

        TreeMap<Integer, Integer> f = new TreeMap();
        ArrayList<Integer> frames = reader.getFrameLen();

        for (int i = 0; i < frames.size(); i++) {
            if (f.containsKey(frames.get(i))) {
                f.put(frames.get(i), f.get(frames.get(i)) + 1);
            } else {
                f.put(frames.get(i), 1);
            }
        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Frame Length");
        yAxis.setLabel("Count");
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Frame Length Statistics");
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<Integer, Integer> rt: f.entrySet()) {
            Integer k = rt.getKey();
            String ky = k.toString();
            Integer v = rt.getValue();

            series.getData().add(new XYChart.Data(ky,v));
        }

        barChart.getData().add(series);
        barChart.setPrefSize(centerPane.getWidth(), centerPane.getHeight());

        final Label af = new Label("test");

        centerPane.setContent(af);
        centerPane.setContent(barChart);
    }

    public void connectionNum() {

        Integer udpc = reader.getUDPConn();
        String udpConn = udpc.toString();
        Integer tcpc = reader.getTCPConn();
        String tcpConn = tcpc.toString();
        Integer arpc = reader.getARP();
        String arpConn = arpc.toString();
        Integer icmpc = reader.getICMP();
        String icmpConn = icmpc.toString();
        int total = tcpc + udpc + arpc + icmpc;
        int tcpP = tcpc * 100 / total;
        int udpP = udpc * 100 / total;
        int arpP = arpc * 100 / total;
        int icmpP = icmpc * 100 / total;

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("TCP " + tcpP + "%", reader.getTCPConn()),
                new PieChart.Data("UDP " + udpP + "%", reader.getUDPConn()),
                new PieChart.Data("ARP " + arpP + "%", reader.getARP()),
                new PieChart.Data("ICMP " + icmpP + "%", reader.getICMP()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setStyle("-fx-font: 24px arial");
        chart.setTitle("Protocol Connections");
        chart.setPrefSize(centerPane.getWidth(), centerPane.getHeight());

        final Label udp = new Label("UDP: " + udpConn);
        udp.setStyle("-fx-font: 24px arial");
        final Label tcp = new Label("TCP: " + tcpConn);
        tcp.setStyle("-fx-font: 24px arial");
        final Label arp = new Label("ARP: " + arpConn);
        arp.setStyle("-fx-font: 24px arial");
        final Label icmp = new Label("ICMP: " + icmpConn);
        icmp.setStyle("-fx-font: 24px arial");
        tcp.setTranslateY(24);
        arp.setTranslateY(48);
        icmp.setTranslateY(72);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(udp, tcp, arp, icmp, chart);
        centerPane.setContent(vbox);
    }

    public void payloadLength() {
        ArrayList<Integer> pl = reader.getPayloadLength();
        TreeMap<Integer, Integer> count = new TreeMap();
        double averageLength = 0;

        for (int i = 0; i < pl.size(); i++) {
            averageLength += pl.get(i);

            if(count.containsKey(pl.get(i))) {
                count.put(pl.get(i), count.get(pl.get(i)) + 1);
            } else {
                count.put(pl.get(i), 1);
            }
        }

        double al = Math.round(averageLength / pl.size());

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Payload Sizes");
        yAxis.setLabel("Count of Payload Sizes");
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Payload Size Count");
        XYChart.Series series = new XYChart.Series();

        for (Map.Entry<Integer, Integer> rt: count.entrySet()) {
            Integer k = rt.getKey();
            String ke = String.valueOf(k);
            Integer v = rt.getValue();

            series.getData().add(new XYChart.Data(ke,v));
        }

        barChart.getData().add(series);
        VBox vbox = new VBox();
        String alToString = String.valueOf(al);
        Label av = new Label("Average Payload Size: " + alToString);
        av.setStyle("-fx-font: 24px arial");
        vbox.getChildren().add(av);
        barChart.setPrefSize(centerPane.getWidth(), centerPane.getHeight());
        vbox.getChildren().add(barChart);
        centerPane.setContent(vbox);
    }

    public String getFilePath() {
        return this.filePath;
    }

    /* public void doneButtonAction() {
        String[] destList = reader.getDestIP();
        String[] sourceList = reader.getSourceIP();
        String[] ipList = (String[]) ArrayUtils.addAll(destList, sourceList);

        for (int i = 0; i < ipList.length; i++) {
            Label ipL = new Label(ipList[i]);
            allIP.getChildren().add(ipL);
        }

        for (int i = 0; i < destList.length; i++) {
            Label destL = new Label(destList[i]);
            destIP.getChildren().add(destL);
        }

        for (int i = 0; i < sourceList.length; i++) {
            Label sourceL = new Label(sourceList[i]);
            sourceIP.getChildren().add(sourceL);
        }
    } */

    public void Processor() {
        PcapParser parser = new PcapParser();
        FormatUtils format = new FormatUtils();

        PcapPacketArrayList t = parser.runPcapParser(getFilePath());

        Ip4 ip = new Ip4();
        Ip6 ip6 = new Ip6();
        Tcp tcp = new Tcp();
        Http http = new Http();
        Ethernet eth = new Ethernet();
        Payload payload = new Payload();
        byte[] sIP;
        byte[] dIP;
        String s = "";
        String d = "";
        String ipv6 = "";
        Tcp.Timestamp timestamp = new Tcp.Timestamp();
        Icmp icmp = new Icmp();
        Udp udp = new Udp();
        Arp arp = new Arp();

        reader.arrivalTime.clear();
        reader.frameLength.clear();
        reader.numOfProtocols.clear();
        reader.destinationIP.clear();
        reader.sourceIP.clear();
        reader.ipPacketLength.clear();
        reader.destinationPorts.clear();
        reader.sourcePorts.clear();
        reader.payloadLength.clear();
        reader.windowSize.clear();
        reader.tcpPacketLength.clear();
        reader.udpPacketLength.clear();
        reader.seqNum.clear();
        reader.ackNum.clear();
        reader.setARP(0);
        reader.setConn(0);
        reader.setICMP(0);
        reader.setTCP(0);
        reader.setUDP(0);

        for (int i = 0; i < t.size(); i++) {
            reader.incrementConn();

            if (t.get(i).hasHeader(eth)) {
                reader.addPayloadLen(t.get(i).getHeader(eth).getPayloadLength());
            }

            if (t.get(i).hasHeader(tcp)) {
                reader.incrementTCP();
            } else if (t.get(i).hasHeader(udp)) {
                reader.incrementUDP();
            } else if (t.get(i).hasHeader(arp)) {
                reader.incrementARP();
            } else if (t.get(i).hasHeader(icmp)) {
                reader.incrementICMP();
            }

            reader.addFrameLen(t.get(i).getCaptureHeader().caplen());

            if (t.get(i).getHeader(eth).type() == 2048) {
                sIP = t.get(i).getHeader(ip).source();
                dIP = t.get(i).getHeader(ip).destination();
                String sp = "";
                String dp = "";
                if (t.get(i).hasHeader(tcp)) {
                    sp = ":" + String.valueOf(t.get(i).getHeader(tcp).source());
                    dp = ":" + String.valueOf(t.get(i).getHeader(tcp).destination());
                } else if (t.get(i).hasHeader(udp)) {
                    sp = ":" + String.valueOf(t.get(i).getHeader(udp).source());
                    dp = ":" + String.valueOf(t.get(i).getHeader(udp).destination());
                } else {
                    sp = "";
                    dp = "";
                }
                d = format.ip(dIP) + dp;
                s = format.ip(sIP) + sp;
                reader.addSourceIP(s);
                reader.addDestIP(d);
            } else if (t.get(i).getHeader(eth).type() == 34525) {
                sIP = t.get(i).getHeader(ip6).source();
                dIP = t.get(i).getHeader(ip6).destination();
                String sp = "";
                String dp = "";
                if (t.get(i).hasHeader(tcp)) {
                    sp = String.valueOf(t.get(i).getHeader(tcp).source());
                    dp = String.valueOf(t.get(i).getHeader(tcp).destination());
                } else if (t.get(i).hasHeader(udp)) {
                    sp = String.valueOf(t.get(i).getHeader(udp).source());
                    dp = String.valueOf(t.get(i).getHeader(udp).destination());
                }
                d = format.ip(dIP) + ":" + dp;
                s = format.ip(sIP) + ":" + sp;
                reader.addSourceIP(d);
                reader.addDestIP(s);
            } else if (t.get(i).getHeader(eth).type() == 2054) {
                sIP = t.get(i).getHeader(eth).source();
                dIP = t.get(i).getHeader(eth).destination();
                String sp = "";
                String dp = "";
                d = format.ip(dIP) + dp;
                s = format.ip(sIP) + sp;
                reader.addSourceIP(s);
                reader.addDestIP(d);
            }

            if (t.get(i).hasHeader(tcp)) {
                String ack = String.valueOf(t.get(i).getHeader(tcp).ack());
                String seq = String.valueOf(t.get(i).getHeader(tcp).seq());
                String ws = String.valueOf(t.get(i).getHeader(tcp).window());
                reader.addSeq(seq);
                reader.addAck(ack);
                reader.addWindowSize(ws);
            } else {
                reader.addWindowSize("");
                reader.addSeq("");
                reader.addAck("");
            }

            long ti = t.get(i).getCaptureHeader().timestampInMillis();
            DateFormat date = new SimpleDateFormat("MM/dd/yy HH:mm:ss:SSS");
            String fdate = date.format(ti);
            //String tim = format.formatTimeInMillis(ti);
            //String[] split = tim.split(" ");

            reader.addTime(fdate);

            if (t.get(i).hasHeader(ip)) {
                String p = "";
                if (t.get(i).hasHeader(icmp)) {
                    p = "ICMP";
                    reader.addIPPacketLen(t.get(i).getHeader(ip).length());
                    reader.addProtocols(p);
                } else {
                    p = String.valueOf(t.get(i).getHeader(ip).typeEnum());
                    reader.addIPPacketLen(t.get(i).getHeader(ip).length());
                    reader.addProtocols(p);
                }
            } else if (t.get(i).hasHeader(ip6)) {
                reader.addIPPacketLen(t.get(i).getHeader(ip6).length());
                reader.addProtocols("");
            } else if (t.get(i).hasHeader(arp)) {
                reader.addIPPacketLen(0);
                reader.addProtocols("ARP");
            }
        }

        //String[] dest = reader.getDestIP();
        //System.out.println(reader.getSeq().size());

        //for (int v = 0; v < dest.length; v++) {
        //    System.out.println(dest[v]);
        //}
    }
}
