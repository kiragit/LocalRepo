000100* Sample COBOL program
000200 IDENTIFICATION DIVISION.
000300 PROGRAM-ID. hello.
000310 DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 CNT PIC 9(03) VALUE 0.
000400 PROCEDURE DIVISION.
       PERFORM 100 TIMES
           ADD 1 TO CNT
000500     DISPLAY "COUNT = " CNT
       END-PERFORM
000600 STOP RUN.
