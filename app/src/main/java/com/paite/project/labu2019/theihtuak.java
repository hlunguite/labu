package com.paite.project.labu2019;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;


public class theihtuak extends AppCompatActivity {
    ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theihtuak);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.labu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        WebView webView = findViewById(R.id.webTheituak);

        String htmlString = "<html> <head><title>Page Title</title>";
        htmlString += "  </head> <body > <h1>Chibai</h1>";
        htmlString += "<p>Hiai <i><b>Labu App</b></i> a laate a publisher/laa phuaktute kiang ah ahi thei tanin phalna kila hi </p>";
        htmlString += "<p>Biakna La, Pathian Ngaih La leh Suangmantam Evangelical Baptist Convention theihpihna leh phalna <i>(Vide EOM 480th Sitting, Thupukna 4na, May 14, 2019)</i> a kizang ahi. </p>";

        htmlString += "<p>Biakna leh Phatna a laa omte mun tuamtuam a kipat kila khawm ahi a, laa teng a vek in aphuak tute phal na kila kim sipsip theilou hi. Himahleh laphuaktu bangzah hiamte kiang ah zatphalna kila hi. </p>";

        htmlString += "<p><b><i>Labu App</i></b> ahihleh laa kaikhawm tu leh munkhat a koihkhawmtu ahi.</p></br><h3>DISCLAIMER</h3>";


        htmlString += "   <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</p>";
        htmlString += "</body></html>";

        webView.loadData(htmlString, "text/html", null);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Log.d("Search menu click ", "id " + id + " " + android.R.id.home );
        if (id == android.R.id.home) {
            navigateUp();
        }
        return super.onOptionsItemSelected(item);
    }
    public void navigateUp() {

        finish();
    }

}
