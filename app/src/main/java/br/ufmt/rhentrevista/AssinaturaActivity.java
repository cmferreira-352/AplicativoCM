package br.ufmt.rhentrevista;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class AssinaturaActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad  assinaturaPad;
    private Button salvar;
    private Button limpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_assinatura);
        salvar = (Button) findViewById(R.id.save_button);
        limpar = (Button) findViewById(R.id.clear_button);
        salvar.setEnabled(false);
        limpar.setEnabled(false);
        assinaturaPad = (SignaturePad) findViewById(R.id.assinaturaPad);
        assinaturaPad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Faz nada
            }

            @Override
            public void onSigned() {
                salvar.setEnabled(true);
                limpar.setEnabled(true);
            }

            @Override
            public void onClear() {
                salvar.setEnabled(false);
                limpar.setEnabled(false);
            }
        });
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int posicao = intent.getIntExtra("posicao",-1);
                ListaDeCandidatos candidatos = new ListaDeCandidatos();
                Intent resultadoIntent = new Intent();
                resultadoIntent.putExtra("posicao", posicao);
                Log.d("POSCICAO", "A POSICAO QUE CHEGA É: "+posicao);
                if (posicao != -1) setResult(101, resultadoIntent);
                Bitmap signatureBitmap = assinaturaPad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap,candidatos.getCandidatos().get(posicao).getCpf())) {
                    Toast.makeText(AssinaturaActivity.this, "Assinatura salva na galeria", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssinaturaActivity.this, "Não foi possível armazenar a assinatura", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assinaturaPad.clear();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AssinaturaActivity.this, "Não é possível salvar imagens em armazenamento externo", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Pega o diretorio para a pasta pública de imagens do usuário
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature, String cpfCandidato) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Assinatura_%s.jpg", cpfCandidato));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        AssinaturaActivity.this.sendBroadcast(mediaScanIntent);
    }

    /**
     * Checa se o app tem permissão para escrever do armazenamento do dispositivo
     * <p/>
     * Se o aplicativo não tiver permissão, o usuário poderá conceder permissão
     *
     * @param activity A activity que pedirá as pemissões
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Checa se há permissão para escrever
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Não há permissão então pedimos ao usuário
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
