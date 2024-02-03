import segno
from segno import helpers
links = ['https://www.chnu.edu.ua/','http://ptcsi.chnu.edu.ua/departments/', 'https://sites.google.com/chnu.edu.ua/pzks/%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BD%D0%B0?authuser=0https://sites.google.com/chnu.edu.ua/pzks/%D0%B3%D0%BE%D0%BB%D0%BE%D0%B2%D0%BD%D0%B0?authuser=0']
names = ['chnu.png', 'nniftkn.png', 'kafedra.png']
for i in range(3):
    qrLink = segno.make(links[i])
    qrLink.save(names[i], scale = 10)


email = "***"
password = "***"

#auth_string = f"https://{email}:{password}@github.com/ArtemBabaiev/ue5-PenaltyGame"
auth_string = f"{email}:{password}"
qr = segno.make(auth_string)
qr.save("email_auth_qr.png", scale=10)

qrcode = helpers.make_wifi(ssid='***', password='***', security='WPA')
qrcode.save('wifi-access.png', scale=10)

card = helpers.make_mecard(
    name="Artem Babaiev",
    email="babaiev.artem@chnu.edu.ua",
    phone='+380969077503',
    url=['https://github.com/ArtemBabaiev'],
    birthday='20020718',
    city='Chernivtsy',
    country='Ukraine'
)
card.save('mycontact.png', scale=10)
