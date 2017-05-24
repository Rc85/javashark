package PcapApp;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.util.PcapPacketArrayList;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by roger85 on 3/22/2017.
 */
public class PcapParser {

    public PcapPacketArrayList runPcapParser(String filename) {
        String file = filename;
        StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Pcap.openOffline(file, errbuf);

        if (pcap == null) {
            System.err.printf("Error opening file: " + errbuf.toString());
        }

        JPacketHandler<String> handler = new JPacketHandler<String>() {

            @Override
            public void nextPacket(JPacket arg0, String arg1) {
                System.out.println(arg0.toString());
            }
        };

        PcapPacketHandler<PcapPacketArrayList> jpacketHandler = new PcapPacketHandler<PcapPacketArrayList>() {

            public void nextPacket(PcapPacket packet, PcapPacketArrayList packetList) {
                packetList.add(packet);
                /* Tcp tcp = new Tcp();
                Time time = new Time(packet.getCaptureHeader().timestampInMillis());
                Ip4 ip = new Ip4();
                FormatUtils format = new FormatUtils();

                byte[] sIP = packet.getHeader(ip).source();
                byte[] dIP = packet.getHeader(ip).destination();

                String s = format.ip(sIP);
                String d = format.ip(dIP);

                PcapHeader ph = new PcapHeader();

                if (packet.hasHeader(tcp)) {
                    System.out.println(s + " " + d);
                } */
            }
        };

        try {
            PcapPacketArrayList list = new PcapPacketArrayList();

            pcap.loop(pcap.LOOP_INFINITE, jpacketHandler, list);

            return list;
        } finally {
            pcap.close();
        }
    }
}
