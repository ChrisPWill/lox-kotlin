#include <doctest/doctest.h>
#include <memory>

struct DLinkedNode {
  std::shared_ptr<DLinkedNode> next;
  std::weak_ptr<DLinkedNode> prev;
  int data;

  explicit DLinkedNode(int val) : data(val) {}
};

class DLinkedIterator {
private:
  std::shared_ptr<DLinkedNode> current;

public:
  int get() { return current->data; }

  // TODO: Improve signature of this for chaining
  bool next() {
    if (current->next == nullptr) {
      return false;
    }

    current = current->next;
    return true;
  }

  bool prev() {
    if (current->prev.lock() == nullptr) {
      return false;
    }

    current = current->next;
    return true;
  }

  explicit DLinkedIterator(std::shared_ptr<DLinkedNode> current) {
    this->current = current;
  };
};

class DLinkedList {
private:
  std::shared_ptr<DLinkedNode> head;
  std::shared_ptr<DLinkedNode> tail;

public:
  DLinkedIterator startIterator() { return DLinkedIterator(head); }
  DLinkedIterator endIterator() { return DLinkedIterator(tail); }

  void insertEnd(int val) {
    auto newNode = std::make_shared<DLinkedNode>(val);

    if (!head) {
      head = tail = newNode;
      return;
    }
    tail->next = newNode;
    newNode->prev = tail;
    tail = newNode;
  }
};

TEST_CASE("testing the doubly linked list") {
  auto list = std::make_shared<DLinkedList>();
  list->insertEnd(123);
  list->insertEnd(345);
  list->insertEnd(678);

  auto it = list->startIterator();
  auto hasNext = it.next();
  CHECK(hasNext);
  CHECK(it.get() == 345);
  hasNext = it.next();
  CHECK(hasNext);
  CHECK(it.get() == 678);
}
