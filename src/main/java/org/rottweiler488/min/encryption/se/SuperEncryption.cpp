#include <iostream>
using namespace std;

int value(char c) {
    switch (c) {
        case 'I': return 1;   case 'V': return 5;
        case 'X': return 10;  case 'L': return 50;
        case 'C': return 100; case 'D': return 500;
        case 'M': return 1000;
        default:  return 0;
    }
}

int romanToInt(string s) {
    int res = 0;

    for (int i = 0; i < s.size(); i++) {

        int next = 0;
        int curr = value(s[i]);

        if (i + 1 < s.size())
            next = value(s[i + 1]);

        if (curr < next) res -= curr;
        else             res += curr;
    }
    return res;
}

int main() {
    string s;

    cout << "Введите римское число: "; cin >> s;
    cout << "Декодированное число: " << romanToInt(s) << endl;
}