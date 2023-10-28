package util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.nio.charset.Charset;

import domain.Eleicao;

public class InputServices {
    public static BufferedReader createBufferedReader(String path) throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path), Charset.forName("ISO-8859-1")));
            return br;
        } catch (IOException e) {
            throw e;
        }
    }

    public static void processarArquivoCandidatos(BufferedReader bufferCandidatos, Eleicao eleicao){
        
    }
}
