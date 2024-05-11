package com.folioreader.Labu;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDFReader {

    private PdfRenderer _pdfRenderer;
    private PdfRenderer.Page _currentPage;
    private ParcelFileDescriptor _parcelFileDescriptor;
    private String _pdfFile;
    private String _pdfFilePath;
    private int _height;
    private int _width;
    private boolean _isFileOpen;
    private static PDFReader _pdfReader;
    private PDFReader() {
        _isFileOpen = false;
    }

    public void openPDFFile(String filepath, String file) {

        closeRanderer();
        _pdfFile = file;
        _pdfFilePath = filepath;
        _isFileOpen = false;

        openRenderer();
        Log.d("PDFREADER", "open pdf reader " + _isFileOpen);
    }
    public static PDFReader get() {
        if (_pdfReader == null) {
            synchronized (PDFReader.class) {
                if (_pdfReader == null) {

                    _pdfReader = new PDFReader();
                }
            }
        }

        return _pdfReader;
    }

    private void openRenderer() {
        try {
            _isFileOpen = false;
            AssetManager assetManager = LabuApplication.getApplication().getAssets();

            // In this sample, we read a PDF from the assets directory.
            File file = new File( LabuApplication.getApplication().getCacheDir(), _pdfFile);
            if (!file.exists()) {
                // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
                // the cache directory.
                InputStream asset = assetManager.open(_pdfFilePath + File.separator + _pdfFile);
                FileOutputStream output = new FileOutputStream(file);
                final byte[] buffer = new byte[1024];
                int size;
                while ((size = asset.read(buffer)) != -1) {
                    output.write(buffer, 0, size);
                }
                asset.close();
                output.close();
            }
            _parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            // This is the PdfRenderer we use to render the PDF.
            if (_parcelFileDescriptor != null) {
                _isFileOpen = true;
                _pdfRenderer = new PdfRenderer(_parcelFileDescriptor);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeRanderer() {
        if (_isFileOpen == false) return;
        _isFileOpen = false;
        if (_currentPage != null) {
            _currentPage.close();
        }
        _pdfRenderer.close();
        try {
            _parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Bitmap showPage(int pageindex, int require_height, int require_width) {
        if ((_isFileOpen == false) || _pdfRenderer.getPageCount() <= pageindex || pageindex < 0 ) {

            Log.d("PDFREADER  return null",  "_isFileOpen " + _isFileOpen  +  "pageindex " + pageindex + " page count " + _pdfRenderer.getPageCount() );
            return null;
        }
        // Make sure to close the current page before opening another one.
        if (null != _currentPage) {
            _currentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        _currentPage = _pdfRenderer.openPage(pageindex);

        Log.d("Window Size PDF" , "w " + require_width + " height " + require_height);

        int w = _currentPage.getWidth();
        int h = _currentPage.getHeight();
        Log.d("PDF page size ", "width " + w + " height " + h);

        if (require_width == 0) require_width = w;
        if (require_height == 0) require_height = h;

        double ratio = 1;

        if (require_width < w ||  require_height < h)
        {
            ratio = Math.min(require_width / w, require_height / h);
            if (ratio <= 0) {
                ratio = 1;
            }
            require_width = (int) Math.ceil(w * ratio);
            require_height = (int) Math.ceil(h * ratio);
        }

      //  bm_w = bm_w * w / 72;
       // bm_h = bm_h * h / 72;

        Log.d("PDF new size " , "new w " + require_width+ " " + require_height );


        if (require_height == 0 || require_width == 0) {
            return null;
        }

        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(require_width, require_height,
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        _currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
       // imageViewPdf.setImageBitmap(bitmap);
        //updateUi();

        return bitmap;
    }
}
