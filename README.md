


![phone-call](https://user-images.githubusercontent.com/78859953/121731274-65e36180-caf9-11eb-97ea-18ea0814e3de.png) Bu bir telefon çağrı uygulamasıdır. SQLite ile çalışmaktadır.

<h2><a id="user-content-giriş-ekranı" class="anchor" aria-hidden="true" href="#giriş-ekranı"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Giriş Ekranı</h2>
<p>Kullanıcı bu bölümde email ve şifre bölümlerini girdikten sonra giriş yapabilir. Eğer üye değilse en altta bulunan "Hesabınız yok mu? Kayıt Ol"
yazısına tıkladıktan sonra kayıt olabilir. Şayet üye ve şifresini unutmuş ise şifresini değiştirmek için "Şifremi unuttum" bölümüne gidebilir.</p>
<p><a target="_blank" rel="noopener noreferrer" href="https://user-images.githubusercontent.com/78859953/115146999-aaa2cd00-a061-11eb-9abe-9d2240be80a9.png"><img src="https://user-images.githubusercontent.com/78859953/115146999-aaa2cd00-a061-11eb-9abe-9d2240be80a9.png" alt="girisekrani" style="max-width:100%;"></a></p>
<h2><a id="user-content-kayıt-ol-ekranı" class="anchor" aria-hidden="true" href="#kayıt-ol-ekranı"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Kayıt Ol Ekranı</h2>
<p>Kullanıcı üye olmak istiyorsa bu alanda hiçbir alanı boş bırakamaz. Eğer sistemde kayıt olmak için kullandığı email adresi mevcut ise kod hata döndürecek şekilde ayarlanmıştır. Burada kullanıcının şifresi md5 formatında kaydedilir. Şayet kullanıcı üye ise yine burada en altta bulunan " Hesabınız mı var? Giriş Yap" yazısına tıklaması sonucunda giriş yap bölümüne geçecektir. Kullanıcı üyelik işlemini tamamladıktan sonra profil fotoğrafı eklenmesi istenmektedir.</p>
<p><a target="_blank" rel="noopener noreferrer" href="https://user-images.githubusercontent.com/78859953/115147098-0a00dd00-a062-11eb-94d3-071c4a6f2f1e.png"><img src="https://user-images.githubusercontent.com/78859953/115147098-0a00dd00-a062-11eb-94d3-071c4a6f2f1e.png" alt="girisekranikaydol" style="max-width:100%;"></a></p>
<h2><a id="user-content-profil-fotoğrafı-ekleme-ekranı" class="anchor" aria-hidden="true" href="#profil-fotoğrafı-ekleme-ekranı"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z"></path></svg></a>Profil Fotoğrafı Ekleme Ekranı</h2>
<p>Kullanıcı üye olduktan sonra sistem yeni üyeden bir resim ister eğer kullanıcı bir resim eklemezse sistem otomatik olarak bir resim dosyası ekler. Kullanıcı bu resmi profil sayfasından güncelleyebilir. Sistem kullanıcıdan burada dahili hafızaya ulaşmak için izin ister. Devam et'e tıklandıktan sonra kullanıcı anasayfaya ulaşır.</p>
<p><a target="_blank" rel="noopener noreferrer" href="https://user-images.githubusercontent.com/78859953/115148455-5bac6600-a068-11eb-9905-b9a90a1c9b12.png"><img src="https://user-images.githubusercontent.com/78859953/115148455-5bac6600-a068-11eb-9905-b9a90a1c9b12.png" alt="birresimekle" style="max-width:100%;"></a></p>
</article>
  </div>


</div>

 # Anasayfa Sayfasının Tasarımı
 Kullanıcı giriş yaptığında yada kayıt ol bölümünden sonra buraya yönlendirilecektir. Bu sayfanın açılış anında sistem kullanıcıdan kişilerini ve çağrı yönlendirmesi için izin isteyecektir. Burada alt bölümünde bir "BottomNavigator" vardır. Bu bölüm için üç adet "Fragment" oluşturulmuştur. Menu aşagıda sabit kalacaktır ve iç sayfalar değişecek şekilde ayarlanmıştır. Kullanıcı burada kişilerine, çağrı ayarlarına ve profil bölümüne ulaşabilir.
 
 ![Anasayfa](https://user-images.githubusercontent.com/78859953/121729304-fd938080-caf6-11eb-82ba-51ca18a27854.png)

# Çağrı Sayfasının Tasarımı
Bu bölümde kullanıcı çağrı reddetme, çağrı reddetme için kullanılacak mesaj ve çağrı kaydını görebilecektir. Bu bölümde çagrı mesajları veri tabanında bir tabloda kullanıcıya özel olacak şekilde saklanmaktadır. Kullanıcı ekledigi bütün mesajları burada görüntüleme imkanına sahiptir. Çağrı reddetme bölümü kullanımı için önce bir mesaj seçilmeli, mesaj yok ise yeni mesaj eklenmeli ve o eklenen mesaj seçilmesi gerekmektedir. Bu sayfanın tasarımda yeni mesaj için açılan pencere (dialog) ve çagrı reddetme için ise açılan bir panel kullanılmıştır.

![cagri_1](https://user-images.githubusercontent.com/78859953/121730171-ffaa0f00-caf7-11eb-9264-ed9526f393d0.jpg)

![cagri_2](https://user-images.githubusercontent.com/78859953/121730199-0769b380-caf8-11eb-9166-0a912483bb50.jpg)
