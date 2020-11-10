package com.company;

import java.io.*;
import java.net.DatagramPacket;

public class DatagramWrapper {
    private Message message;
    private Node node;

    private int timesSent = 0;

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
    private ObjectOutputStream objectOutputStream;

    public DatagramWrapper(Message message, Node node) throws IOException {
        this.message = message;
        this.node = node;

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    }

    public DatagramWrapper(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        this.node = new Node(datagramPacket.getAddress(), datagramPacket.getPort());
        this.message = deserializeObject(datagramPacket.getData());

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    }

    public synchronized DatagramPacket convertToDatagramPacket() throws IOException {
        byte[] toSend = serializeObject(message);
        return new DatagramPacket(toSend, toSend.length, node.getAddress(), node.getPort());
    }

    private synchronized byte[] serializeObject(Object object) throws IOException {
        objectOutputStream.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    private synchronized <T> T deserializeObject(byte[] rawData) throws IOException, ClassNotFoundException {
        return (T) new ObjectInputStream(new ByteArrayInputStream(rawData)).readObject();
    }

    public Node getNode() {
        return node;
    }

    public Message getMessage() {
        return message;
    }

    public int getTimesSent() {
        return timesSent;
    }

    public void increaseTimesSent() {
        timesSent++;
    }
}