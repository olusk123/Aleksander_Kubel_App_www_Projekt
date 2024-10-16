from django.db import models

class Kategoria(models.Model):
    nazwa = models.CharField(max_length=100, unique=True)
    opis = models.TextField(blank=True)

    def __str__(self):
        return self.nazwa


class Produkt(models.Model):
    nazwa = models.CharField(max_length=200)
    opis = models.TextField()
    cena = models.DecimalField(max_digits=10, decimal_places=2)
    w_magazynie = models.IntegerField()
    kategoria = models.ForeignKey(Kategoria, on_delete=models.CASCADE, related_name='produkty')
    zdjecie = models.ImageField(upload_to='produkty/', blank=True, null=True)

    def __str__(self):
        return self.nazwa


class Klient(models.Model):
    imie = models.CharField(max_length=100)
    nazwisko = models.CharField(max_length=100)
    telefon = models.CharField(max_length=15, blank=True)

    def __str__(self):
        return f"{self.imie} {self.nazwisko}"

class Zamowienie(models.Model):
    klient = models.ForeignKey(Klient, on_delete=models.CASCADE, related_name='zamowienia')
    data_zamowienia = models.DateTimeField(auto_now_add=True)
    status = models.CharField(
        max_length=20,
        choices=[('przyjęte', 'Przyjęte'), ('wysłane', 'Wysłane'), ('dostarczone', 'Dostarczone')],
        default='przyjęte'
    )
    calkowita_kwota = models.DecimalField(max_digits=10, decimal_places=2)

    def __str__(self):
        return f"Zamówienie {self.id} - {self.klient}"

class PozycjaZamowienia(models.Model):
    zamowienie = models.ForeignKey(Zamowienie, on_delete=models.CASCADE, related_name='pozycje')
    produkt = models.ForeignKey(Produkt, on_delete=models.CASCADE)
    ilosc = models.PositiveIntegerField()
    cena_za_sztuke = models.DecimalField(max_digits=10, decimal_places=2)

    def __str__(self):
        return f"{self.produkt.nazwa} x {self.ilosc}"


