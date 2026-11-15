# Little Farm – CN201 Final Project

เกมฟาร์มด้วย Java + Swing ผู้เล่นรับฟาร์มจากปู่พร้อมหนี้ 5,000G ต้องหาเงินให้ครบใน 30 วัน
เวอร์ชันนี้เพิ่ม **ระบบแปรรูปผลผลิต (เครื่องจักร)** พร้อมปรับโครงสร้างโค้ดและระบบเซฟ

## ภาพรวมสิ่งที่ทีมเพิ่ม

| เกณฑ์ | สิ่งที่ทำ | อยู่ที่ไหน |
|---|---|---|
| 1.1 ระบบใหม่ | `Machine` เป็น abstract class มีคลาสลูก 3 ตัว: `CakeMaker` (หัวผักกาด → เค้ก), `PotatoMasher` (มันฝรั่ง 1 หัว → มันบด 2 ชาม), `JamMaker` (สตรอว์เบอร์รี → แยม และหยุดนับวันตอนฝนตก) สร้างผ่าน `MachineFactory` | `src/Machine.java`, `CakeMaker.java`, `PotatoMasher.java`, `JamMaker.java`, `MachineFactory.java` |
| 1.2 เซฟ/พัง/ปฏิเสธ | `FarmException` มีลูก `SaveFileException` (จำเลขบรรทัดที่เสีย) และ `NotEnoughGoldException` ไฟล์เซฟเสียแล้วเกมไม่ปิด แจ้งเลขบรรทัด และเริ่มฟาร์มใหม่ | `src/SaveGame.java`, `FarmScreen.java`, `Farmer.java` |
| 1.3 Strategy | ราคาขายเป็น `PriceStrategy` (`NormalPrice`, `FestivalPrice`, `HardTimesPrice`) เลือกด้วยคำเดียวตอนเปิดเกมผ่าน `PriceStrategyFactory` | `src/PriceStrategy*.java`, `Main.java` |
| 1.4 Refactoring | กลิ่นเสีย Long Method ใน `SaveGame.loadInto` แก้ด้วย Extract Method มี golden test บันทึกผลก่อน/หลัง | `src/SaveGoldenTest.java`, `test/` |
| 1.5 ทีม Git และ AI | ทุกคน commit งานตัวเอง บันทึกการใช้ AI ใน `AI-log.md` | `AI-log.md` |

## วิธีรัน

```
javac -d out src/*.java
java -cp out Main            # ราคาปกติ
java -cp out Main festival   # เทศกาล ขายได้ +50%
java -cp out Main hard       # ช่วงลำบาก ขายได้ -30%
```

คำที่ใช้ได้: `normal`, `festival`, `hard` (พิมพ์คำอื่นจะใช้ `normal` และแสดงคำเตือน)

## ไฟล์เซฟตัวอย่าง

- `data/sample_save.txt` ไฟล์เซฟที่มีข้อมูลเครื่องจักร สำหรับเปิดในวันนำเสนอ
- `data/demo_save.txt` ไฟล์เซฟสำหรับเดโมเครื่องจักร 3 แบบ

วิธีเปิด: คัดลอกไปทับ `littlefarm_save.txt` ที่โฟลเดอร์บนสุด แล้วเลือก Continue ในเกม

```
cp data/sample_save.txt littlefarm_save.txt
java -cp out Main
```

## ทดสอบ golden output (เกณฑ์ 1.4)

```
java -cp out SaveGoldenTest > test/golden_check.txt
diff test/golden_before.txt test/golden_check.txt && echo "no differences"
```

## โครงสร้างโปรเจกต์

```
src/                  โค้ดทั้งหมด (ไม่แยก package ตามคำสั่ง javac ของโจทย์)
data/                 ไฟล์เซฟตัวอย่างและไฟล์เซฟสำหรับเดโม
test/                 golden output ก่อนและหลัง refactor
design.pdf            class diagram, exception family, Strategy, ตาราง smell/refactoring/commit
AI-log.md             บันทึกการใช้ AI ของทีม
```

## ประวัติ commit
- commit แรกของ repo คือ `f6b4c69` มีแค่ README เพราะ GitHub สร้างให้ตอนเปิด repo
- `84a62e9` คือโค้ดตั้งต้นจาก LittleFarm.zip ที่ยังไม่ได้แก้อะไรเลย
- งานของทีมทั้งหมดอยู่หลัง `84a62e9`