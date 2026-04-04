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
  // Indirection operator
  int &operator*() const { return current->data; }
  // Member access operator
  int *operator->() const { return &(current->data); }

  bool operator==(const DLinkedIterator &other) const = default;

  DLinkedIterator &operator++() {
    if (current) {
      current = current->next;
    }

    return *this;
  }

  DLinkedIterator &operator--() {
    if (current) {
      current = current->prev.lock();
    }

    return *this;
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
  DLinkedIterator begin() { return DLinkedIterator(head); }
  DLinkedIterator end() { return DLinkedIterator(nullptr); }

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

TEST_CASE("testing increment/decrement") {
  auto list = std::make_shared<DLinkedList>();
  list->insertEnd(123);
  list->insertEnd(345);
  list->insertEnd(678);

  auto itr = list->begin();
  CHECK(*itr == 123);
  CHECK(*(++itr) == 345);
  CHECK(*(--itr) == 123);
  ++itr;
  ++itr;
  CHECK(*itr == 678);
}

TEST_CASE("test iterating through list") {
  auto list = std::make_shared<DLinkedList>();
  list->insertEnd(123);
  list->insertEnd(345);
  list->insertEnd(678);

  int count = 0;
  for (auto it = list->begin(); it != list->end(); ++it) {
    count++;
  }
  CHECK(count == 3);
}
