package cn.objectspace.webssh.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NDArrayIOUtils {

    public static float[][] read2dNpyFile(String file) throws IOException {
        float[] tmp = readNpyFile(file);
        if (tmp == null) {
           return null;
        }
        //todo 写死了
        int col = (int)Math.sqrt(tmp.length);
        float[][] res = new float[col][col];
        int index = 0;
        for (int i = 0; i < col;i+=1) {
            for (int j = 0; j < col; j+=1) {
                res[i][j] = index+=1;
            }
        }
        return res;
    }
    public static float[] readNpyFile(String file) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file));
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(bytes));
        byte major = input.readByte(); // major version number
        byte minor = input.readByte(); // minor version number
        short header_len = input.readShort(); // length of the header
        byte[] header = new byte[header_len];
        input.readFully(header);

        float[] values = null;
        if (bytes.length - header_len - 10 > 0) {
            byte[] data = new byte[bytes.length - header_len - 10];
            input.readFully(data);
            ByteBuffer buf = ByteBuffer.wrap(data);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            values = new float[data.length / 4];
            buf.asFloatBuffer().get(values);
        }
        return values;
    }

    public static void writeNpyFile(Path file, float[] values) throws IOException {
        byte[] bytes = createNpyBytes(values);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file.toFile()));
        stream.write(bytes);
        stream.flush();
        stream.close();
    }


    private static byte[] createNpyBytes(float[] values) {
        ByteBuffer buf = ByteBuffer.allocate(values.length * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.asFloatBuffer().put(values);

        byte[] data = buf.array();

        byte[] header = ("{\"descr\": \"" + "<f4" + "\", \"fortran_order\": false, \"shape\": " + "[" + values.length + "]" + "}\n").getBytes();

        ByteBuffer b = ByteBuffer.wrap(new byte[10 + header.length + data.length]);
        b.put((byte) 147); // magic string
        b.put((byte) 78); // magic string
        b.put((byte) 85); // magic string
        b.put((byte) 77); // magic string
        b.put((byte) 1);  // major version number
        b.put((byte) 0);  // minor version number
        b.putShort((short) header.length);
        b.put(header);
        b.put(data);

        return b.array();
    }


}