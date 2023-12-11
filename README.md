---

# Coin Track

Coin Track, kullanıcılara coingecko.com sitesinden alınan API ile coin listelerini görüntüleyebilecekleri ve bu coinlerin detaylarını, son fiyatlarını, açıklamalarını ve detaylarını görebilecekleri bir platform sunar. Ayrıca kullanıcılar favori coinlerini işaretleyebilir ve daha kolay bir şekilde takip edebilir. Tüm coinler arasında arama yapabilirler.

## Özellikler

- Coin Listesi: Coingecko.com API'si kullanılarak coin listesi görüntüleme.
- Coin Detayları: Coin detayları, fiyatları, açıklamaları ve detayları görüntüleme.
- Favori Ekleme: Kullanıcıların favori coinlerini işaretleyip takip etme özelliği.
- Arama: Tüm coinler arasında arama yapabilme.

## Mimarî ve Teknolojiler

Projede S.O.L.I.D prensipleri baz alınarak, feature ve katman bazlı bir mimari yapı izlenmiştir. Bu doğrultuda Data-Domain-UI şeklinde her bir özellik kendi içerisinde ayrılmıştır. MVVM desenine dayalı bir mimari tasarım kullanılmıştır.

### Kullanılan Teknolojiler

- Version Catalog
- Navigation Component
- Flows Reactive Programming
- Dagger - Hilt for Dependency Injection
- Retrofit
- Gson for Serialization
- Glide for Image Caching and Loading
- Room for Local Database
- Lottie
- Swipe Refresh Layout
- Firestore
- Firebase Auth

## Test Kullanıcı Bilgileri

- **E-posta:** test@gmail.com
- **Şifre:** test123

## Kurulum ve Kullanım

Projeyi çalıştırmak için aşağıdaki adımları izleyebilirsiniz:

1. Proje dosyalarını klonlayın ya da indirin.
2. Android Studio'da projeyi açın.
3. Gerekli bağımlılıkları yükleyin ve yapılandırmaları yapın.
4. Firebase hesabı oluşturun ve gerekli ayarları projeye entegre edin.
5. Test kullanıcı bilgileriyle giriş yaparak uygulamayı test edin.
