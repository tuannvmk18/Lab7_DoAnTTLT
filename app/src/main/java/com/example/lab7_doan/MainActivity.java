package com.example.lab7_doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class QuestionNare {
    public String ID;
    public String Q;
    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
}

public class MainActivity extends AppCompatActivity {
    TextView Cauhoi, Ketqua;
    RadioGroup RG;
    Button BT;
    RadioButton A, B, C, D;
    int pos = 0;
    int kq = 0;
    ArrayList<QuestionNare> L = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cauhoi = (TextView) findViewById(R.id.TxtCauhoi);
        Ketqua = (TextView) findViewById(R.id.TxtKetqua);
        RG = (RadioGroup) findViewById(R.id.RadioGroupTraloi);
        BT = (Button) findViewById(R.id.BtnTraloi);
        A = (RadioButton) findViewById(R.id.RdbA);
        B = (RadioButton) findViewById(R.id.RdbB);
        C = (RadioButton) findViewById(R.id.RdbC);
        D = (RadioButton) findViewById(R.id.RdbD);

        ReadData();

        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idCheck = RG.getCheckedRadioButtonId();
                switch (idCheck) {
                    case R.id.RdbA:
                        if (L.get(pos).Answer.compareTo("A") == 0) kq = kq + 1;
                        break;
                    case R.id.RdbB:
                        if (L.get(pos).Answer.compareTo("B") == 0) kq = kq + 1;
                        break;
                    case R.id.RdbD:
                        if (L.get(pos).Answer.compareTo("D") == 0) kq = kq + 1;
                        break;
                }
                pos++;

                if (pos >= L.size()) {
                    Intent intent = new Intent(MainActivity.this, activity_ketqua.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("KQ", kq);
                    bundle.putInt("Socau", pos);
                    intent.putExtra("MyPackage", bundle);

                    startActivity(intent);
                    pos = 0; //Cho vị trí pos về câu hỏi đầu tiên
                    kq = 0; //cho số câu hỏi đúng bằng 0, để làm lại
                    Display(pos);
                } else Display(pos);
            }
        });
    }

    void Display(int i) {
        Cauhoi.setText(L.get(i).Q);
        A.setText(L.get(i).AnswerA);
        B.setText(L.get(i).AnswerB);
        C.setText(L.get(i).AnswerC);
        D.setText(L.get(i).AnswerD);
        Ketqua.setText("Câu đúng:" + kq);
        RG.clearCheck(); //xóa checked
    }

    void ReadData() {
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = DBF.newDocumentBuilder();
            InputStream in = getAssets().open("Data.xml");
            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement();
            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node instanceof Element) {
                    Element Item = (Element) node;
                    NodeList listChild = Item.getElementsByTagName("ID");
                    String ID = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("Question");
                    String Question = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerA");
                    String AnswerA = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerB");
                    String AnswerB = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerC");
                    String AnswerC = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("AnswerD");
                    String AnswerD = listChild.item(0).getTextContent();
                    listChild = Item.getElementsByTagName("Answer");
                    String Answer = listChild.item(0).getTextContent();

                    QuestionNare Q1 = new QuestionNare();
                    Q1.ID = ID;
                    Q1.Q = Question;
                    Q1.AnswerA = AnswerA;
                    Q1.AnswerB = AnswerB;
                    Q1.AnswerC = AnswerC;
                    Q1.AnswerD = AnswerD;
                    Q1.Answer = Answer;
                    L.add(Q1);
                }
                ;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
