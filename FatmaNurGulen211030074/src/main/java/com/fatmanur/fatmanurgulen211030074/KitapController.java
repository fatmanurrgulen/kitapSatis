package com.fatmanur.fatmanurgulen211030074;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
    Kitap işlemleri için KitapController adında Controller sınıfı
    Initializable arayünü kalıtlayarak kitap bilgilerini dosyadan
    okur. Dosya ismi: kitapbilgileri.dat
 */
public class KitapController implements Initializable {

    @FXML
    private Button btnKaydet;

    @FXML
    private Button btnSil;

    @FXML
    private ComboBox<String> cmbAd;

    @FXML
    private ComboBox<String> cmbDil;

    @FXML
    private ComboBox<String> cmbEv;

    @FXML
    private ComboBox<String> cmbTur;

    @FXML
    private DatePicker dpYayinTarih;

    @FXML
    private Spinner<Integer> spBasimYil;

    @FXML
    private TextField txtAd;

    @FXML
    private TextField txtBasimYil;

    @FXML
    private TextField txtDil;

    @FXML
    private TextField txtEv;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTur;

    @FXML
    private TextField txtYayinTarihi;

    @FXML
    private TextField txtYazar;

    @FXML
    private TextField txtBasimYilShow;

    @FXML
    private TextField txtDilShow;

    @FXML
    private TextField txtEvShow;

    @FXML
    private TextField txtISBNShow;

    @FXML
    private TextField txtTurShow;

    @FXML
    private TextField txtYayinTarihShow;

    @FXML
    private TextField txtYazarShow;

    /*
        Kitap adı seçilen comboboxda seçim
        yapıldığında kitap detaylarını
        ilgili metin kutularına aktarır.
     */
    @FXML
    void KitapGoster(ActionEvent event) {
        // seçilen kitabın indeksini al
        int secim = cmbAd.getSelectionModel().getSelectedIndex();

        Kitap kitap = kitaplar.get(secim);
        txtISBNShow.setText(kitap.getISBN());
        txtYazarShow.setText(kitap.getYazar());
        txtYayinTarihShow.setText(kitap.getYil().toString());
        txtDilShow.setText(kitap.getDil());
        txtTurShow.setText(kitap.getTur());
        txtBasimYilShow.setText(kitap.getBasimYil().toString());
        txtEvShow.setText(kitap.getEv());
    }

    /*
        İlgili kontrollerde yer alan kitap bilgilerinden
        Kitap objesi oluşturup dosyaya kayıt eder.
        Kayıt başarılı ile kitap bilgilerinin bulunduğu
        combox kontrolünü günceller.
     */
    @FXML
    void kitapKayit(ActionEvent event) {

        //Kitap adı ve ISBN bilgileri boş olamaz.
        String kitapAd = txtAd.getText();
        if ("".equals(kitapAd)) {
            showErrorAlert("Kitap adı girilmemiş");
            return;
        }
        String isbn = txtISBN.getText();
        if ("".equals(isbn)) {
            showErrorAlert("ISBN girilmemiş");
            return;
        }

        //Aynı ISBN ancak bir tane kitap olabilir.
        for (int i=0; i<kitaplar.size(); i++) {
            if (isbn.equals(kitaplar.get(i).getISBN())) {
                showErrorAlert("Aynı ISBN numaralı kitap var.");
                return;
            }
        }

        Kitap kitap = new Kitap(
            txtAd.getText(),
            txtISBN.getText(),
            txtYazar.getText(),
            cmbDil.getValue(),
            cmbTur.getValue(),
            dpYayinTarih.getValue(),
            cmbEv.getValue(),
            spBasimYil.getValue()
        );

        kitaplar.add(kitap);

        kitaplarKayit(kitap.getAd());

    }

    /*
    Kitap adlarının bulunduğu combobox kontrolünündeki
    seçilmiş kitabı siler.
    Kitap Bilgilerinin yer aldığı kontrolleri boşaltır.
    Kitap adlarının bulunduğu combobox kontrolünü günceller.
     */
    @FXML
    void kitapSil(ActionEvent event) {
        //Kayıtlı kitap kontrolü
        if ( 0 == kitaplar.size()) {
            showErrorAlert("Liste boş.");
            return;
        }

        //Kitap seçimi kontrolü
        int secim = cmbAd.getSelectionModel().getSelectedIndex();
        if (secim == -1 ) {
            showErrorAlert("Kitap seçilmemiş.");
            return;
        }

        kitaplar.remove(secim);

        kitaplarKayit("");

        showInfoAlert("Seçili kitap silindi.");

        fillComboBoxAd();

        txtISBNShow.setText("");
        txtYazarShow.setText("");
        txtYayinTarihShow.setText("");
        txtDilShow.setText("");
        txtTurShow.setText("");
        txtBasimYilShow.setText("");
        txtEvShow.setText("");

    }

    private ArrayList<Kitap> kitaplar = new ArrayList<Kitap>();

    /*
    İlk çalıştırmada kitap bilgilerini dosyadan okur.
    kitap bilgilerinin saklandığı array listi günceller.
    Kitap adlarının bulunduğu combobox kontrolünü günceller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbDil.getItems().clear();
        cmbDil.getItems().addAll("Türkçe","İngilizce","Almanca", "İspanyolca", "Çince");

        cmbTur.getItems().clear();
        cmbTur.getItems().addAll("Roman","Hikaye","Şiir", "Belgesel", "Bilim Kurgu");

        cmbEv.getItems().clear();
        cmbEv.getItems().addAll("Güven","Hızlı","Renk", "Papatya", "Alim");

        spBasimYil.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2022, 2022, 1));

        try {
            FileInputStream fis = new FileInputStream("kitapbilgileri.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            kitaplar = (ArrayList<Kitap>) ois.readObject();
            ois.close();
            fis.close();

            fillComboBoxAd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Hata mesajı gösterimi
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("HATA");
        alert.setHeaderText(message);
        alert.show();
        return;
    }

    /*
    Bilgi mesajı gösterimi
     */
    private void showInfoAlert(String message) {
        Alert infomessage = new Alert(Alert.AlertType.INFORMATION);
        infomessage.setTitle("BİLGİ");
        infomessage.setHeaderText(message);
        infomessage.show();
        return;
    }

    /*
    kitap bilgilerinin saklandığı array listi dosyaya saklar.
     */
    private void kitaplarKayit(String kitapad) {
        try {
            FileOutputStream fos = new FileOutputStream("kitapbilgileri.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(kitaplar);
            oos.close();
            fos.close();

            if (!"".equals(kitapad)) {
                showInfoAlert(kitapad + " adlı kitap kaydedildi.");
                cmbAd.getItems().add(kitapad);
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    /*
    Kitap adlarının gösterildiği combobox kontrolünü günceller.
     */
    private  void fillComboBoxAd() {
        cmbAd.getItems().clear();
        for (int i=0; i<kitaplar.size(); i++) {
            cmbAd.getItems().add(kitaplar.get(i).getAd());
        }
    }
}
